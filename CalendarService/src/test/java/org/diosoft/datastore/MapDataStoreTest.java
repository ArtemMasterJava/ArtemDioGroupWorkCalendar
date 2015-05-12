package org.diosoft.datastore;

import org.diosoft.model.Event;
import org.diosoft.model.Person;
import org.junit.Test;

import java.util.*;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;


public class MapDataStoreTest {

    @Test
    public void testAddEvent() {

        String title = "Test event";
        String description = "Test description";
        UUID id = UUID.randomUUID();
        GregorianCalendar startDate = new GregorianCalendar(2015, Calendar.APRIL, 30, 10, 0);
        GregorianCalendar endDate = new GregorianCalendar(2015, Calendar.APRIL, 30, 12, 30);
        List<Person> attendees = Arrays.asList(
                new Person.Builder().firstName("John").lastName("Peters").email("peters@gmail.com").build(),
                new Person.Builder().firstName("Mister").lastName("Snake").email("snake@yahoo.com").build()
        );
        Event expectedValue = new Event.Builder()
                .title(title)
                .description(description)
                .id(id)
                .startDate(startDate)
                .endDate(endDate)
                .attendees(attendees)
                .build();

        MapDataStore testClass = new MapDataStore();

        testClass.addEvent(expectedValue);
        Event returnedValue = testClass.getEventForTest(expectedValue.getId());

        assertEquals(expectedValue, returnedValue);
    }

    @Test
    public void testGetEvent() {

        String title = "Test event";
        String description = "Test description";
        UUID id = UUID.randomUUID();
        GregorianCalendar startDate = new GregorianCalendar(2015, Calendar.APRIL, 30, 10, 0);
        GregorianCalendar endDate = new GregorianCalendar(2015, Calendar.APRIL, 30, 12, 30);
        List<Person> attendees = Arrays.asList(
                new Person.Builder().firstName("John").lastName("Peters").email("peters@gmail.com").build(),
                new Person.Builder().firstName("Mister").lastName("Snake").email("snake@yahoo.com").build()
        );
        Event expectedValue = new Event.Builder()
                .title(title)
                .description(description)
                .id(id)
                .startDate(startDate)
                .endDate(endDate)
                .attendees(attendees)
                .build();

        MapDataStore testClass = new MapDataStore();

        testClass.addEventForTest(expectedValue);
        Event returnedValue = testClass.getEvent(expectedValue.getId());

        assertEquals(expectedValue, returnedValue);
    }

    //local code review (vtegza): remove or fix it @ 11.05.15
  /*  @Test
    public void testGetAllEvents() {
        String title1 = "Test event";
        String title2 = "Test event";
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        Event event1 = new Event.Builder()
                .title(title1)
                .id(id1)
                .build();
        Event event2 = new Event.Builder()
                .title(title1)
                .id(id1)
                .build();

        int expectedValue = 2;

        MapDataStore testClass = new MapDataStore();

        testClass.addEventForTest(event1);
        testClass.addEventForTest(event2);
        int returnedValue = testClass.getAllEvents().size();

        assertEquals(expectedValue, returnedValue);
    }*/

    @Test
    public void testRemoveEvent() {
        String title = "Test event";
        String description = "Test description";
        UUID id = UUID.randomUUID();
        GregorianCalendar startDate = new GregorianCalendar(2015, Calendar.APRIL, 30, 10, 0);
        GregorianCalendar endDate = new GregorianCalendar(2015, Calendar.APRIL, 30, 12, 30);
        List<Person> attendees = Arrays.asList(
                new Person.Builder().firstName("John").lastName("Peters").email("peters@gmail.com").build(),
                new Person.Builder().firstName("Mister").lastName("Snake").email("snake@yahoo.com").build()
        );
        Event expectedValue = new Event.Builder()
                .title(title)
                .description(description)
                .id(id)
                .startDate(startDate)
                .endDate(endDate)
                .attendees(attendees)
                .build();

        MapDataStore testClass = new MapDataStore();

        testClass.addEventForTest(expectedValue);
        Event returnedValue = testClass.removeEvent(expectedValue.getId());

        assertEquals(expectedValue, returnedValue);
    }
    @Test
    public void testFreePersonInCurrentTime(){
        MapDataStore store = mock(MapDataStore.class);

        boolean returned = store.freePersonInCurrentTime(new Person.Builder().build(), new GregorianCalendar());

        assertFalse(returned);
    }
}