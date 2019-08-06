package io.pivotal.pal.tracker;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTimeEntryRepository implements  TimeEntryRepository{

    List<TimeEntry> timeEntries = new ArrayList<>();
    //Map<Long,TimeEntry> timeEntriesMap = new HashMap<Long,TimeEntry>();
    private long nextId = 0L;
    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        generateNextId();
        TimeEntry newTimeEntry = new TimeEntry (nextId, timeEntry.getProjectId(), timeEntry.getUserId(), timeEntry.getDate(), timeEntry.getHours());
        timeEntries.add(newTimeEntry);
        return newTimeEntry;
    }

    private void generateNextId (){
        if (!timeEntries.isEmpty()){
            for(TimeEntry timeEntry : timeEntries)
            {
                if(nextId < timeEntry.getId())
                    nextId = timeEntry.getId();
            }
        }
        nextId++;
    }
    @Override
    public TimeEntry find(long timeEntryId) {
        for(TimeEntry timeEntry:timeEntries)
        {
            if(timeEntryId==timeEntry.getId())
                return timeEntry;
        }
        return null;
    }

    @Override
    public List<TimeEntry> list() {

        return timeEntries;
    }

    @Override
    public TimeEntry update(long timeEntryId, TimeEntry timeEntrytobeUpdated) {
        boolean findItem = false;
        for(TimeEntry timeEntry:timeEntries)
        {
            if(timeEntryId==timeEntry.getId())
            {
                timeEntries.remove(timeEntry);
                findItem = true;
                break;

            }
        }
        if (findItem){
            TimeEntry te = new TimeEntry(timeEntryId, timeEntrytobeUpdated.getProjectId(), timeEntrytobeUpdated.getUserId(), timeEntrytobeUpdated.getDate(), timeEntrytobeUpdated.getHours());
            timeEntries.add(te);
            return te;
        }
        return null;
    }

    @Override
    public void delete(long timeEntryId) {

        for(TimeEntry timeEntry:timeEntries)
        {
            if(timeEntryId==timeEntry.getId())
            {
                timeEntries.remove(timeEntry);
                return;

            }
        }


    }
}
