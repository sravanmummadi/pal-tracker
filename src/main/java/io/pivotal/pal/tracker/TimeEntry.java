package io.pivotal.pal.tracker;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;


public class TimeEntry {


    private final long projectId;
    private final long userId;
    private final LocalDate date;
    private final int hours;
    private final long id;
    //private static long nextId = 0;

    public TimeEntry(long projectId, long userId, LocalDate date, int hours) {


        this.id=1;
        this.projectId = projectId;
        this.userId = userId;
        this.date = date;
        this.hours = hours;
    }

    public TimeEntry(long id, long projectId, long userId, LocalDate date, int hours) {
        this.id = id;
        this.projectId = projectId;
        this.userId = userId;
        this.date = date;
        this.hours = hours;
    }

    public TimeEntry() {
        this.id = 1;
        this.projectId = -1;
        this.userId = -1;
        this.date = LocalDate.now();
        this.hours = -1;

    }


    public long getProjectId() {
        return projectId;
    }

    public long getUserId() {
        return userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getHours() {
        return hours;
    }

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeEntry timeEntry = (TimeEntry) o;
        return projectId == timeEntry.projectId &&
                userId == timeEntry.userId &&
                hours == timeEntry.hours &&
                id == timeEntry.id &&
                date.equals(timeEntry.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId, userId, date, hours, id);
    }

    @Override
    public String toString() {
        return "TimeEntry{" +
                "projectId=" + projectId +
                ", userId=" + userId +
                ", date=" + date +
                ", hours=" + hours +
                ", id=" + id +
                '}';
    }
}
