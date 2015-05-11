package org.diosoft.service;

import org.diosoft.datastore.DataStore;
import org.diosoft.model.Event;
import org.diosoft.model.Person;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

public class CalendarServiceImpl implements CalendarService {

    private final DataStore dataStore;

    public CalendarServiceImpl (DataStore dataStore) {
        this.dataStore = dataStore;
    }

    @Override
    public void addEvent(String title, String description, GregorianCalendar startDate, GregorianCalendar endDate,
                         List<Person> attendees) throws RemoteException {
        dataStore.addEvent(new Event.Builder()
                .id(makeId())
                .title(title)
                .description(description)
                .startDate(startDate)
                .endDate(endDate)
                .attendees(attendees)
                .build());
    }

    @Override
    public Event getEvent(UUID id) throws RemoteException {
        return dataStore.getEvent(id);
    }

    @Override
    public void addAllDayEvent(String title, String description, GregorianCalendar date, List<Person> attendees) throws RemoteException {
        date.set(Calendar.HOUR, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(date.getTime());
        gregorianCalendar.add(Calendar.HOUR, 23);
        gregorianCalendar.add(Calendar.MINUTE, 59);
        gregorianCalendar.add(Calendar.SECOND, 59);

        addEvent(title,description, date, gregorianCalendar, attendees);
    }

    @Override
    public List<Event> getAllEvents() throws RemoteException {
        return dataStore.getAllEvents();
    }

    @Override
    public Event removeEvent(UUID id) throws RemoteException {
        return dataStore.removeEvent(id);
    }

    public UUID makeId() {
        return UUID.randomUUID();
    }

    @Override
    //local code review (vtegza): move most of the logic to service layer - data/repository should be simple @ 11.05.15
    public List<Calendar[]> checkAvailability(List<Person> attendees) {
        return dataStore.checkAvailability(attendees);
    }

    @Override
    public List<Event> getEventsByDate(GregorianCalendar date) {
        return dataStore.getEventsByDate(date);
    }

    @Override
    public boolean freePersonInCurrentTime(Person person, GregorianCalendar time) {
        return dataStore.freePersonInCurrentTime(person, time);
    }


}
