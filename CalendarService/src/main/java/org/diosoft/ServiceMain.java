package org.diosoft;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.rmi.RemoteException;

public class ServiceMain {


    public static void main(String[] args) throws RemoteException {

        ApplicationContext context = new ClassPathXmlApplicationContext("serviceApplicationContext.xml");
        System.out.println("Calendar Service is running...");
        //local code review (vtegza): use registerShutdownHook @ 11.05.15
    }


}
