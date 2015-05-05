package org.diosoft.datastore;

import org.diosoft.model.Event;

import java.util.List;
import java.util.UUID;

public interface DataStore {

    void addEvent(Event event);

    Event getEvent(UUID id);

    List<Event> getAllEvents();

    Event removeEvent(UUID id);

}