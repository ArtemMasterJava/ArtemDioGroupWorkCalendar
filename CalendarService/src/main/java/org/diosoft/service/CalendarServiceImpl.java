package org.diosoft.service;

import org.diosoft.datastore.DataStore;
import org.diosoft.model.Event;

import java.rmi.RemoteException;
import java.util.*;

public class CalendarServiceImpl implements CalendarService {

    private final DataStore dataStore;

    public CalendarServiceImpl (DataStore dataStore) {
        this.dataStore = dataStore;
    }

    @Override
    public void addEvent(String title, String description, GregorianCalendar startDate, GregorianCalendar endDate,
                         String[] attendees) throws RemoteException {
        dataStore.addEvent(new Event.Builder()
                .id(makeId())
                .title(title)
                .description(description)
                .startDate(startDate)
                .endDate(endDate)
                .attendees(Arrays.asList(attendees))
                .build());
    }

    @Override
    public Event getEvent(UUID id) throws RemoteException {
        return dataStore.getEvent(id);
    }

    @Override
    public List<Event> getAllEvents() throws RemoteException {
        return dataStore.getAllEvents();
    }

    @Override
    public Event removeEvent(UUID id) throws RemoteException {
        return dataStore.removeEvent(id);
    }

    @Override
    public List<Calendar[]> checkAvailability(String[] attendees) {
        return null;
    }

    public UUID makeId() {
        return UUID.randomUUID();
    }

    @Override
    public List<Event> getEventsByDate(GregorianCalendar date) {
        return dataStore.getEventsByDate(date);
    }
}
