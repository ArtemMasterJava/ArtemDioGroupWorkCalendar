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

        System.out.println(service.getAllEvents());

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

        GregorianCalendar date = new GregorianCalendar(2015, Calendar.MAY, 14);

        service.addAllDayEvent("Test_All_Day", "Some Description", date, attendees);

        System.out.println(service.getAllEvents());
    }

    public static String formatDate(Calendar date) {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("dd.MM.yyyy HH:mm");
        return sdf.format(date.getTime());
    }
}
