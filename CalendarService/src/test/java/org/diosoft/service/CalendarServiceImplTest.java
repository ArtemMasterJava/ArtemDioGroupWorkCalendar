package org.diosoft.service;

import org.diosoft.datastore.DataStore;
import org.diosoft.datastore.MapDataStore;
import org.diosoft.model.Event;
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
        String[] attendees = {"peters@gmail.com", "snake@yahoo.com"};

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
    public void test() {


        //List<Calendar[]> checkAvailability(Person… persons)
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