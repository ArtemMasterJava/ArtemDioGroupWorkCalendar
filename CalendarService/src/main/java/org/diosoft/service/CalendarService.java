package org.diosoft.service;

import org.diosoft.model.Event;
import org.diosoft.model.Person;

import java.nio.file.Path;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

public interface CalendarService extends Remote {

    void addEvent (String title, String description, GregorianCalendar startDate, GregorianCalendar endDate,
                   List<Person> attendees) throws RemoteException;

    Event getEvent(UUID id) throws RemoteException;

    void addAllDayEvent(String title, String description, GregorianCalendar date,
                         List<Person> attendees) throws RemoteException;

    List<Event> getAllEvents() throws RemoteException;

    Event removeEvent(UUID id) throws RemoteException;

    List<Calendar[]> checkAvailability(List<Person> attendees) throws RemoteException;

    List<Event> getEventsByDate(GregorianCalendar date) throws RemoteException;

    boolean freePersonInCurrentTime(Person person, GregorianCalendar time) throws RemoteException;

    void fillStorage(Path path) throws RemoteException;
}
