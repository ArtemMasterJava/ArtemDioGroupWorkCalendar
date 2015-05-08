package org.diosoft.service;

import org.diosoft.datastore.DataStore;
import org.diosoft.datastore.MapDataStore;
import org.diosoft.model.Event;
import org.diosoft.model.Person;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.*;

public class CalendarServiceImplTest {

    @Test
    public void testAddEvent() throws Exception {

        // initialize variable inputs
        String title = "Test Event";
        String description = "Some Description";
        GregorianCalendar startDate = new GregorianCalendar(2015, Calendar.MAY, 12, 10, 0);
        GregorianCalendar endDate = new GregorianCalendar(2015, Calendar.MAY, 12, 12, 0);
        List<Person> attendees = Arrays.asList(
                new Person.Builder().firstName("John").lastName("Peters").email("peters@gmail.com").build(),
                new Person.Builder().firstName("Mister").lastName("Snake").email("snake@yahoo.com").build()
        );
        Event event = new Event.Builder().build();
        // initialize mocks
        DataStore dataStore = mock(DataStore.class);
        ArgumentCaptor<Event> argumentCaptor = ArgumentCaptor.forClass(Event.class);

        // initialize class to test
        CalendarService service = new CalendarServiceImpl(dataStore);

        // invoke method on class to test
        service.addEvent(title, description,startDate, endDate, attendees);

        // verify mock expectations
        verify(dataStore).addEvent(argumentCaptor.capture());
    }

    @Test
    public void testGetEvent() throws Exception {

        // initialize variable inputs
        Event expectedValue = new Event.Builder().build();
        UUID id = UUID.randomUUID();

        // initialize mocks
        DataStore dataStore = mock(DataStore.class);
        when(dataStore.getEvent(id)).thenReturn(expectedValue);

        // initialize class to test
        CalendarService service = new CalendarServiceImpl(dataStore);

        // invoke method on class to test
        Event returnedValue = service.getEvent(id);

        // assert return value
        assertEquals(expectedValue, returnedValue);

        // verify mock expectations
        verify(dataStore).getEvent(id);
    }

    @Test
    public void testGetEventsByDate() throws Exception{
        GregorianCalendar date = new GregorianCalendar(2015, Calendar.MAY, 12, 10, 0);
        List<Event> expectedValue = new ArrayList<Event>();

        DataStore dataStore = mock(DataStore.class);
        when(dataStore.getEventsByDate(date)).thenReturn(expectedValue);

        CalendarService service = new CalendarServiceImpl(dataStore);
        List<Event> returnedValue = service.getEventsByDate(date);

        assertEquals(expectedValue,returnedValue);
        verify(dataStore).getEventsByDate(date);
    }

    @Test
    public void testGetAllEvents() throws Exception {

        // initialize variable inputs
        List<Event> expectedValue = new ArrayList<Event>();

        // initialize mocks
        DataStore dataStore = mock(DataStore.class);
        when(dataStore.getAllEvents()).thenReturn(expectedValue);

        // initialize class to test
        CalendarService service = new CalendarServiceImpl(dataStore);

        // invoke method on class to test
        List<Event> returnedValue = service.getAllEvents();

        // assert return value
        assertEquals(expectedValue, returnedValue);

        // verify mock expectations
        verify(dataStore).getAllEvents();
    }

    @Test
    public void testRemoveEvent() throws Exception {

        // initialize variable inputs
        Event expectedValue = new Event.Builder().build();
        UUID id = UUID.randomUUID();

        // initialize mocks
        DataStore dataStore = mock(DataStore.class);
        when(dataStore.removeEvent(id)).thenReturn(expectedValue);

        // initialize class to test
        CalendarService service = new CalendarServiceImpl(dataStore);

        // invoke method on class to test
        Event returnedValue = service.removeEvent(id);

        // assert return value
        assertEquals(expectedValue, returnedValue);

        // verify mock expectations
        verify(dataStore).removeEvent(id);
    }

    @Test
    public void testMakeId() {

        UUID expectedValue = UUID.randomUUID();

        CalendarServiceImpl testClass = new CalendarServiceImpl(new MapDataStore());

        UUID returnedValue = testClass.makeId();

        assertNotEquals(expectedValue, returnedValue);
    }

    @Test
    public void testCheckAvailability() throws Exception {

        //List<Calendar[]> checkAvailability(Person… persons)

        // initialize variable inputs
        List<Calendar[]> expectedValue = new ArrayList<Calendar[]>();
        List<Person> attendees = Arrays.asList(
                new Person.Builder().firstName("John").lastName("Peters").email("peters@gmail.com").build(),
                new Person.Builder().firstName("Mister").lastName("Snake").email("snake@yahoo.com").build()
        );

        // initialize mocks
        DataStore dataStore = mock(DataStore.class);

        // initialize class to test
        CalendarService service = new CalendarServiceImpl(dataStore);
        when(service.checkAvailability(attendees)).thenReturn(expectedValue);

        // invoke method on class to test
        List<Calendar[]> returnedValue = service.checkAvailability(attendees);

        // assert return value
        assertEquals(expectedValue, returnedValue);

        // verify mock expectations
        verify(dataStore).getAllEvents();
    }

    @Test
    public void mockTestExample() {

        // initialize variable inputs

        // initialize mocks

        // initialize class to test

        // invoke method on class to test

        // assert return value

        // verify mock expectations
    }
}