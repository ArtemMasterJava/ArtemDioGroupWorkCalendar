package org.diosoft.datastore;

import org.diosoft.model.Event;
import org.diosoft.model.Person;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

public interface DataStore {

    void addEvent(Event event);

    void addAllDayEvent(Event event);

    Event getEvent(UUID id);

    List<Event> getAllEvents();

    Event removeEvent(UUID id);

    List<Event> getEventsByDate(GregorianCalendar date);

    List<Calendar[]> checkAvailability(List<Person> attendees);

    boolean freePersonInCurrentTime(Person person, GregorianCalendar time);

    Event addAttenders(String title, List<Person> attenders);
}
