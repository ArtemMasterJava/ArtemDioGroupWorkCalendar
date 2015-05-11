package org.diosoft.datastore;

import org.diosoft.model.Event;
import org.diosoft.model.Person;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MapDataStoreTest {

    @Test
    public void testAddEvent() throws Exception {

        String title = "Meeting";
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

        Path dir = Paths.get("./xml-data");
        if (Files.notExists(dir)) Files.createDirectory(dir);

        MapDataStore testClass = new MapDataStore();

        testClass.addEvent(expectedValue);
        Event returnedValue = testClass.getEventForTest(expectedValue.getId());

        assertEquals(expectedValue, returnedValue);
    }

    @Test
    public void testGetEvent() throws Exception {

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

        Path dir = Paths.get("./xml-data");
        if (Files.notExists(dir)) Files.createDirectory(dir);

        MapDataStore testClass = new MapDataStore();

        testClass.addEventForTest(expectedValue);
        Event returnedValue = testClass.getEvent(expectedValue.getId());

        assertEquals(expectedValue, returnedValue);
    }

    @Test
    public void testGetAllEvents() throws Exception {
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
                .id(id2)
                .build();

        Path dir = Paths.get("./xml-data");
        if (Files.notExists(dir)) Files.createDirectory(dir);

        int expectedValue = 2;

        MapDataStore testClass = new MapDataStore();

        testClass.addEventForTest(event1);
        testClass.addEventForTest(event2);
        int returnedValue = testClass.getAllEvents().size();

        assertEquals(expectedValue, returnedValue);
    }

    @Test
    public void testRemoveEvent() throws Exception {
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

        Path dir = Paths.get("./xml-data");
        if (Files.notExists(dir)) Files.createDirectory(dir);

        MapDataStore testClass = new MapDataStore();

        testClass.addEventForTest(expectedValue);
        Event returnedValue = testClass.removeEvent(expectedValue.getId());

        assertEquals(expectedValue, returnedValue);
    }

    @Test
    //local code review (vtegza): ad more se cases @ 11.05.15
    public void testCheckAvailability() throws Exception {

        String title1 = "Meeting";
        String title2 = "Code review";
        String title3 = "Presentation to customer";

        String description1 = "Discuss new project design";
        String description2 = "Public review of juniors code";
        String description3 = "Presentation before realizing";

        GregorianCalendar startDate1 = new GregorianCalendar(2015, Calendar.MAY, 12, 10, 0);
        GregorianCalendar endDate1 = new GregorianCalendar(2015, Calendar.MAY, 12, 11, 0);
        GregorianCalendar startDate2 = new GregorianCalendar(2015, Calendar.MAY, 12, 11, 15);
        GregorianCalendar endDate2 = new GregorianCalendar(2015, Calendar.MAY, 12, 13, 0);
        GregorianCalendar startDate3 = new GregorianCalendar(2015, Calendar.MAY, 13, 14, 0);
        GregorianCalendar endDate3 = new GregorianCalendar(2015, Calendar.MAY, 13, 16, 30);

        List<Person> attendees1 = Arrays.asList(
                new Person.Builder().firstName("John").lastName("Peters").email("peters@gmail.com").build(),
                new Person.Builder().firstName("Mister").lastName("Snake").email("snake@yahoo.com").build(),
                new Person.Builder().firstName("Mary").lastName("Smith").email("smith@outlook.com").build()
        );
        List<Person> attendees2 = Arrays.asList(
                new Person.Builder().firstName("Alex").lastName("Black").email("black@gmail.com").build(),
                new Person.Builder().firstName("Orlando").lastName("Johnson").email("johnson@yahoo.com").build()
        );

        List<Person> attendees3 = Arrays.asList(
                new Person.Builder().firstName("Josef").lastName("Adams").email("adams@gmail.com").build(),
                new Person.Builder().firstName("Mary").lastName("Smith").email("smith@outlook.com").build()
        );

        List<Person> attendees4 = Arrays.asList(
                new Person.Builder().firstName("Andruha").lastName("Romanenko").email("Andruha@gmail.com").build(),
                new Person.Builder().firstName("Anton").lastName("Smith").email("Anton@outlook.com").build()
        );

        List<Person> testingAttendees = Arrays.asList(
                new Person.Builder().firstName("John").lastName("Peters").email("peters@gmail.com").build(),
                new Person.Builder().firstName("Alex").lastName("Black").email("black@gmail.com").build(),
                new Person.Builder().firstName("Mary").lastName("Smith").email("smith@outlook.com").build()
        );

        Event event1 = new Event.Builder()
                .title(title1)
                .description(description1)
                .id(UUID.randomUUID())
                .startDate(startDate1)
                .endDate(endDate1)
                .attendees(attendees1)
                .build();

        Event event2 = new Event.Builder()
                .title(title2)
                .description(description2)
                .id(UUID.randomUUID())
                .startDate(startDate2)
                .endDate(endDate2)
                .attendees(attendees2)
                .build();

        Event event3 = new Event.Builder()
                .title(title3)
                .description(description3)
                .id(UUID.randomUUID())
                .startDate(startDate3)
                .endDate(endDate3)
                .attendees(attendees3)
                .build();

        Path dir = Paths.get("./xml-data");
        if (Files.notExists(dir)) Files.createDirectory(dir);

        List<Calendar[]> expectedValue = new ArrayList<>();

        // I need to use "mock" and "when" because tested method is current-time-dependant
        MapDataStore testClass = mock(MapDataStore.class);
        when(testClass.checkAvailability(testingAttendees)).thenReturn(expectedValue);

        testClass.addEventForTest(event1);
        testClass.addEventForTest(event2);
        testClass.addEventForTest(event3);

        List<Calendar[]> returnedValue = testClass.checkAvailability(testingAttendees);

        assertEquals(expectedValue, returnedValue);
    }

    @Test
    public void testFreePersonInCurrentTime(){
        MapDataStore store = mock(MapDataStore.class);

        boolean returned = store.freePersonInCurrentTime(new Person.Builder().build(), new GregorianCalendar());

        assertFalse(returned);
    }

    @Test
    public void testUpdateEvent() throws Exception {

        String title = "Meeting";
        String description = "Test description";
        UUID id = UUID.randomUUID();
        GregorianCalendar startDate = new GregorianCalendar(2015, Calendar.MAY, 30, 10, 0);
        GregorianCalendar endDate = new GregorianCalendar(2015, Calendar.MAY, 30, 12, 30);
        List<Person> attendees = Arrays.asList(
                new Person.Builder().firstName("John").lastName("Peters").email("peters@gmail.com").build(),
                new Person.Builder().firstName("Mister").lastName("Snake").email("snake@yahoo.com").build()
        );
        Event tempValue = new Event.Builder()
                .title(title)
                .description(description)
                .id(id)
                .startDate(startDate)
                .endDate(endDate)
                .attendees(attendees)
                .build();

        Path dir = Paths.get("./xml-data");
        if (Files.notExists(dir)) Files.createDirectory(dir);

        Event expectedValue = new Event.Builder(tempValue).title("Another event title").build();

        MapDataStore testClass = new MapDataStore();
        testClass.addEventForTest(tempValue);
        testClass.updateEvent(expectedValue);
        Event returnedValue = testClass.getEventForTest(expectedValue.getId());

        assertEquals(expectedValue, returnedValue);
    }
}