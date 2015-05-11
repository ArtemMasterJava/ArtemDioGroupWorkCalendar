package org.diosoft;

import org.diosoft.model.Person;
import org.diosoft.service.CalendarService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class ClientMain {

    public static void main(String[] args) throws RemoteException {

        ApplicationContext context = new ClassPathXmlApplicationContext("clientApplicationContext.xml");
        CalendarService service = context.getBean("calendarService", CalendarService.class);

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

        GregorianCalendar startDate1 = new GregorianCalendar(2015, Calendar.MAY, 12, 10, 0);
        GregorianCalendar endDate1 = new GregorianCalendar(2015, Calendar.MAY, 12, 11, 0);
        GregorianCalendar startDate2 = new GregorianCalendar(2015, Calendar.MAY, 12, 11, 15);
        GregorianCalendar endDate2 = new GregorianCalendar(2015, Calendar.MAY, 12, 13, 0);
        GregorianCalendar startDate3 = new GregorianCalendar(2015, Calendar.MAY, 13, 14, 0);
        GregorianCalendar endDate3 = new GregorianCalendar(2015, Calendar.MAY, 13, 16, 30);
        GregorianCalendar date = new GregorianCalendar(2015 , Calendar.MAY, 14);

        service.addEvent("Meeting", "Discuss new project design", startDate1, endDate1, attendees1);
        service.addEvent("Code review", "Public review of juniors code", startDate2, endDate2, attendees2);
        service.addEvent("Presentation to customer", "Presentation before realizing", startDate3, endDate3, attendees3);
        service.addAllDayEvent("All Day Event Title", "holly", date, attendees4);
        //System.out.println(service.getAllEvents());

        List<Person> attendees = Arrays.asList(
                new Person.Builder().firstName("John").lastName("Peters").email("peters@gmail.com").build(),
                new Person.Builder().firstName("Alex").lastName("Black").email("black@gmail.com").build(),
                new Person.Builder().firstName("Mary").lastName("Smith").email("smith@outlook.com").build()
        );

        List<Calendar[]> list = service.checkAvailability(attendees);
        System.out.println("List of periods when attendees are available: ");
        for (Calendar[] calendars : list) {
            System.out.println("start: " + formatDate(calendars[0])+ " end: " + formatDate(calendars[1]));
        }
    }

    public static String formatDate(Calendar date) {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("dd.MM.yyyy HH:mm");
        return sdf.format(date.getTime());
    }

}
