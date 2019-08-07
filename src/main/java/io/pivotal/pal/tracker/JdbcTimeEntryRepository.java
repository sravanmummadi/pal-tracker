package io.pivotal.pal.tracker;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class JdbcTimeEntryRepository implements TimeEntryRepository {
    DataSource dataSource;
    private TimeEntryRepository subject;
    private final RowMapper<TimeEntry> mapper = (rs, rowNum) -> new TimeEntry(
            rs.getLong("id"),
            rs.getLong("project_id"),
            rs.getLong("user_id"),
            rs.getDate("date").toLocalDate(),
            rs.getInt("hours")
    );
    private final ResultSetExtractor<TimeEntry> extractor =
            (rs) -> rs.next() ? mapper.mapRow(rs,1) : null;
    ResultSetExtractor<List<TimeEntry>> extractorList=(rs) -> {int i=0;
    List<TimeEntry> timeEntries=new ArrayList<>();
        while(rs.next())
        {
            timeEntries.add(mapper.mapRow(rs,i++));
        }
        return timeEntries;
    };
    private JdbcTemplate jdbcTemplate;


    public JdbcTimeEntryRepository(DataSource dataSource) {
        this.dataSource=dataSource;


      //  subject = new JdbcTimeEntryRepository(dataSource);

        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        KeyHolder generatedKeyHolder  = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO time_entries (project_id, user_id, date, hours) " +
                            "VALUES (?, ?, ?, ?)",
                    RETURN_GENERATED_KEYS
            );

            statement.setLong(1, timeEntry.getProjectId());
            statement.setLong(2, timeEntry.getUserId());
            statement.setDate(3, Date.valueOf(timeEntry.getDate()));
            statement.setInt(4, timeEntry.getHours());

            return statement;
        }, generatedKeyHolder);
        TimeEntry newTimeEntry = new TimeEntry(generatedKeyHolder.getKey().longValue(),timeEntry.getProjectId(),timeEntry.getUserId(),timeEntry.getDate(),timeEntry.getHours());

        return newTimeEntry;
    }

    @Override
    public TimeEntry find(long timeEntryId) {

        return jdbcTemplate.query("Select * from time_entries where id = ?", new Object[]{timeEntryId},extractor);
    }

    @Override
    public List<TimeEntry> list() {
        return jdbcTemplate.query("Select * from time_entries",extractorList);

    }

    @Override
    public TimeEntry update(long id, TimeEntry timeEntry) {

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE time_entries SET project_id=?, user_id=?, date=?, hours=? WHERE id=?"
            );

            statement.setLong(1, timeEntry.getProjectId());
            statement.setLong(2, timeEntry.getUserId());
            statement.setDate(3, Date.valueOf(timeEntry.getDate()));
            statement.setInt(4, timeEntry.getHours());
            statement.setLong(5, id);

            return statement;
        });
        TimeEntry newTimeEntry = new TimeEntry(id,timeEntry.getProjectId(),timeEntry.getUserId(),timeEntry.getDate(),timeEntry.getHours());

        return newTimeEntry;
    }

    @Override
    public void delete(long id) {

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM time_entries WHERE id=?"
            );
            statement.setLong(1, id);

            return statement;
        });
    }
}
