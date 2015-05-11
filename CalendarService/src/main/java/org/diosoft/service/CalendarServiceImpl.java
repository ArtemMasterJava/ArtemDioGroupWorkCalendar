package org.diosoft.service;

import org.diosoft.adapters.EventAdapter;
import org.diosoft.adapters.PersonAdapter;
import org.diosoft.datastore.DataStore;
import org.diosoft.model.Event;
import org.diosoft.model.Person;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.rmi.RemoteException;
import java.util.*;

import static java.nio.file.FileVisitResult.CONTINUE;

public class CalendarServiceImpl implements CalendarService {

    private final DataStore dataStore;
    EventLoader loader;

    public CalendarServiceImpl (DataStore dataStore) {
        this.dataStore = dataStore;
        loader = new EventLoader();
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

    @Override
    public List<Calendar[]> checkAvailability(List<Person> attendees) {
        return dataStore.checkAvailability(attendees);
    }

    public UUID makeId() {
        return UUID.randomUUID();
    }

    @Override
    public List<Event> getEventsByDate(GregorianCalendar date) {
        return dataStore.getEventsByDate(date);
    }

    @Override
    public boolean freePersonInCurrentTime(Person person, GregorianCalendar time) {
        return dataStore.freePersonInCurrentTime(person, time);
    }

    @Override
    public void fillStorage(Path path) throws RemoteException {

        try {
            Files.walkFileTree(path, loader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateEvent(Event event) throws RemoteException {
        dataStore.updateEvent(event);
    }

    private Event readEvent(String path) throws JAXBException {

        File file = new File(path);
        JAXBContext jaxbContext = JAXBContext.newInstance(EventAdapter.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        EventAdapter eventAdapter = (EventAdapter) jaxbUnmarshaller.unmarshal(file);

        List<Person> attendees = new ArrayList<>();
        if (eventAdapter.getAttendees() != null ) {
            for (PersonAdapter pa : eventAdapter.getAttendees()) {
                Person person = new Person.Builder()
                        .firstName(pa.getFirstName())
                        .lastName(pa.getLastName())
                        .email(pa.getEmail())
                        .build();
                attendees.add(person);
            }
        }

        return new Event.Builder()
                .id(eventAdapter.getId())
                .title(eventAdapter.getTitle())
                .description(eventAdapter.getDescription())
                .startDate(eventAdapter.getStartDate())
                .endDate(eventAdapter.getEndDate())
                .attendees(attendees)
                .build();
    }

    public class EventLoader
            extends SimpleFileVisitor<Path> {

        // Invoke JAXB for the each file and add result to storage
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
            try {
                dataStore.addEvent(readEvent(file.toString()));
            } catch (JAXBException e) {
                e.printStackTrace();
            }
            return CONTINUE;
        }
    }
}
