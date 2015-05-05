package org.diosoft;

import org.diosoft.service.CalendarService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class ClientMain {

    public static void main(String[] args) throws RemoteException {

        ApplicationContext context = new ClassPathXmlApplicationContext("clientApplicationContext.xml");
        CalendarService service = context.getBean("calendarService", CalendarService.class);

        String[] attendees1 = {"smith@gmail.com", "johnson@yahoo.com"};
        String[] attendees2 = {"black@gmail.com", "smith@gmail.com", "adams@gmail.com"};
        String[] attendees3 = {"peters@gmail.com"};

        GregorianCalendar startDate1 = new GregorianCalendar(2015, Calendar.MAY, 12, 10, 0);
        GregorianCalendar endDate1 = new GregorianCalendar(2015, Calendar.MAY, 12, 11, 0);
        GregorianCalendar startDate2 = new GregorianCalendar(2015, Calendar.MAY, 12, 11, 15);
        GregorianCalendar endDate2 = new GregorianCalendar(2015, Calendar.MAY, 12, 13, 0);
        GregorianCalendar startDate3 = new GregorianCalendar(2015, Calendar.MAY, 12, 14, 0);
        GregorianCalendar endDate3 = new GregorianCalendar(2015, Calendar.MAY, 12, 16, 30);

        service.addEvent("Meeting", "Discuss new project design", startDate1, endDate1, attendees1);
        service.addEvent("Code review", "Public review of juniors code", startDate2, endDate2, attendees2);
        service.addEvent("Presentation to customer", "Presentation before realizing", startDate3, endDate3, attendees3);

        System.out.println(service.getAllEvents());
    }

}
