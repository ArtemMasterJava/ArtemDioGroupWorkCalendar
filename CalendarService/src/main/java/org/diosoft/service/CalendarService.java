package org.diosoft.service;

import org.diosoft.model.Event;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

public interface CalendarService extends Remote {

    void addEvent (String title, String description, GregorianCalendar startDate, GregorianCalendar endDate,
                   String[] attendees) throws RemoteException;

    Event getEvent(UUID id) throws RemoteException;

    List<Event> getAllEvents() throws RemoteException;

    Event removeEvent(UUID id) throws RemoteException;

    List<Calendar[]> checkAvailability(String[] attendees) throws RemoteException;
    List<Event> getEventsByDate(GregorianCalendar date) throws RemoteException;
    List<Event> getEventsByDate(GregorianCalendar date) throws RemoteException;
}