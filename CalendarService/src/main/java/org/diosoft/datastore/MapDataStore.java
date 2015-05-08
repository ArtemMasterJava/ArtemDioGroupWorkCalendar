package org.diosoft.datastore;

import org.diosoft.adapters.EventAdapter;
import org.diosoft.model.Event;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.*;

public class MapDataStore implements DataStore {

    private Map<UUID, Event> storage;

    public MapDataStore() {
        this.storage  = new HashMap<UUID, Event>();
    }

    @Override
    public void addEvent(Event event) {
        storage.put(event.getId(), event);
        writeEvent(event);
    }

    @Override
    public Event getEvent(UUID id) {
        return storage.get(id);
    }

    @Override
    public List<Event> getAllEvents() {
        return new ArrayList<Event>(storage.values());
    }

    @Override
    public Event removeEvent(UUID id) {
        Event event = storage.get(id);
        storage.remove(id);
        return event;
    }

    //@Override
    public List<Calendar[]> checkAvailability(String[] attendees) {
        return null;
    }

    void addEventForTest(Event event) {
        storage.put(event.getId(), event);
    }

    Event getEventForTest(UUID id) {
        return storage.get(id);
    }

    @Override
    public List<Event> getEventsByDate(GregorianCalendar date) {
        List<Event> allEvents = this.getAllEvents();
        List<Event> resultList = new ArrayList<Event>();
        for(Event temp : allEvents) {
            if (date.equals(temp.getStartDate())){
                resultList.add(temp);
            }
        }
        return resultList;
    }

    private void writeEvent(Event event){

        JAXBContext jaxbContext = null;

        EventAdapter eventAdapter = new EventAdapter(event);
        try {
            jaxbContext = JAXBContext.newInstance(EventAdapter.class);
            Marshaller m = jaxbContext.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            m.marshal(eventAdapter, new File("./xml-data/"+ event.getTitle() +".xml"));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
