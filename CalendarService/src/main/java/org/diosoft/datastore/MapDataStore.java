package org.diosoft.datastore;

import org.diosoft.model.Event;

import java.util.*;

public class MapDataStore implements DataStore {

    private Map<UUID, Event> storage;

    public MapDataStore() {
        this.storage  = new HashMap<UUID, Event>();
    }

    @Override
    public void addEvent(Event event) {
        storage.put(event.getId(), event);
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
}
