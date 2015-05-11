package org.diosoft;

import org.diosoft.service.CalendarService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.RemoteException;

public class ServiceMain {


    public static void main(String[] args) throws RemoteException {

        ApplicationContext context = new ClassPathXmlApplicationContext("serviceApplicationContext.xml");
        CalendarService service = context.getBean("calendarService", CalendarService.class);
        System.out.println("Loading data from file system...");

        Path path = Paths.get("./xml-data");
        service.fillStorage(path);

        System.out.println(" Done!");
        System.out.println();

        System.out.println("Calendar Service is running...");
    }


}
