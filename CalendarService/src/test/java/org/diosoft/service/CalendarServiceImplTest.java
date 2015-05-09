package org.diosoft.service;

import com.sun.org.apache.xerces.internal.xs.StringList;
import org.junit.Test;
import java.rmi.RemoteException;
import java.util.GregorianCalendar;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;


public class CalendarServiceImplTest {

    @Test
    public void testAddEvent() {

    }

    @Test
    public void testGetEvent() {

    }

    @Test
    public void testGetAllEvents() {

    }

    @Test
    public void testRemoveEvent() {

    }

    @Test
    public void testMakeId() {

    }

    @Test
    public void isAttenderAvailableInCurrentTime() throws RemoteException {

        // initialize mocks
        CalendarService calendarService = mock(CalendarServiceImpl.class);

        boolean returned = calendarService.freeAttenderInCurrentTime(new GregorianCalendar());

        assertFalse(returned);
    }

}