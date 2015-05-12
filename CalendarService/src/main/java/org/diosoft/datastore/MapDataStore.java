package org.diosoft.datastore;

import org.diosoft.adapters.EventAdapter;
import org.diosoft.model.Event;
import org.diosoft.model.Person;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.text.SimpleDateFormat;
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

    // this method return list of free periods for specified attendees
    // from right now to end of current week
    @Override
    public List<Calendar[]> checkAvailability(List<Person> attendees) {

        // get events for specified attendees
        Set<Event> eventSet = new HashSet<>();
        List<Event> allEvents = new ArrayList<Event>(storage.values());
        for (Event event : allEvents) {
            List<Person> persons = event.getAttendees();
            for(Person person : persons) {
                if (attendees.contains(person)) {
                    eventSet.add(event);
                }
            }
        }

        Calendar date = Calendar.getInstance();

        // right now moment in milliseconds:
        long periodStart = date.getTimeInMillis();

        // calculate moment of current week end
        date.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        date.add(Calendar.DATE, 6);
        date.set(Calendar.HOUR, 12);
        date.set(Calendar.MINUTE, 0);

        // moment of current week end in milliseconds:
        long periodEnd = date.getTimeInMillis();

        TreeMap<GregorianCalendar, GregorianCalendar> occupiedPeriods =
                new TreeMap<GregorianCalendar, GregorianCalendar>();
        for(Event event : eventSet) {
                occupiedPeriods.put(event.getStartDate(), event.getEndDate());
        }

        // zero and even members of lists are start dates of periods
        // odd members are end of dates of periods
        List<Long> occupiedPeriodsMillis = new ArrayList<>();
        List<Long> freePeriodsMillis = new ArrayList<>();

        for(Map.Entry<GregorianCalendar, GregorianCalendar> entry : occupiedPeriods.entrySet()) {
            occupiedPeriodsMillis.add(entry.getKey().getTimeInMillis());
            occupiedPeriodsMillis.add(entry.getValue().getTimeInMillis());
        }

        for (int i = 2; i < occupiedPeriodsMillis.size(); i += 2) {
            if (occupiedPeriodsMillis.get(i-1) < occupiedPeriodsMillis.get(i)) {
                freePeriodsMillis.add(occupiedPeriodsMillis.get(i-1));
                freePeriodsMillis.add(occupiedPeriodsMillis.get(i));
            }
        }

        if (freePeriodsMillis.size() >= 2) {

            while (freePeriodsMillis.get(1) < periodStart) {
                freePeriodsMillis.remove(0);
                freePeriodsMillis.remove(0);
            }

            while (freePeriodsMillis.get(freePeriodsMillis.size() - 2) > periodEnd) {
                freePeriodsMillis.remove(freePeriodsMillis.size() - 1);
                freePeriodsMillis.remove(freePeriodsMillis.size() - 1);
            }


            if (occupiedPeriodsMillis.get(0) > periodStart) {
                freePeriodsMillis.add(0, occupiedPeriodsMillis.get(0));
                freePeriodsMillis.add(0, periodStart);
            }

            if (occupiedPeriodsMillis.get(occupiedPeriodsMillis.size() - 1) < periodEnd) {
                freePeriodsMillis.add(occupiedPeriodsMillis.get(occupiedPeriodsMillis.size() - 1));
                freePeriodsMillis.add(periodEnd);
            }
        }

        List<Calendar[]> freePeriods = new ArrayList<>();

        for (int i = 0; i < freePeriodsMillis.size() - 1; i += 2) {
            Calendar first = Calendar.getInstance();
            Calendar second = Calendar.getInstance();

            first.setTime(new Date(freePeriodsMillis.get(i)));
            second.setTime(new Date(freePeriodsMillis.get(i+1)));

            Calendar[] calendar = {first, second};
            freePeriods.add(calendar);
        }
        return freePeriods;
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

    @Override
    public boolean freePersonInCurrentTime(Person person, GregorianCalendar time) {
        boolean result = true;
        for(Map.Entry<UUID, Event> entry : storage.entrySet()) {
            Event value = entry.getValue();
            for(Person personInStore : value.getAttendees()){
                if(personInStore.getEmail().equals(person.getEmail()) && (value.getStartDate().getTimeInMillis() <= time.getTimeInMillis() && value.getEndDate().getTimeInMillis() >= time.getTimeInMillis())){
                    result = false;
                    break;
                }
            }
        }
        return result;
    }

   @Override
    public Event addAttenders(String title, List<Person> attenders) {
            Event newEvent = null;
            try{
                File file = new File("D:\\" + title + ".xml");
                JAXBContext jaxbContext = JAXBContext.newInstance(EventAdapter.class);
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                newEvent = (Event) unmarshaller.unmarshal(file);
                remove(title);
            }catch (JAXBException e){
                e.printStackTrace();
            }

            Event event = new Event.Builder()
                    .title(newEvent.getTitle())
                    .description(newEvent.getDescription())
                    .id(newEvent.getId())
                    .attendees(attenders)
                    .startDate(newEvent.getStartDate())
                    .endDate(newEvent.getEndDate())
                    .build();

            addEvent(event);

            return event;
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

    // internal needs method
    private void printCalendarMap(Map<GregorianCalendar, GregorianCalendar> map) {
        for (Map.Entry<GregorianCalendar, GregorianCalendar> entry : map.entrySet()) {
            System.out.println("Key : " + entry.getKey().getTime()
                    + " Value : " + entry.getValue().getTime());
        }
    }

    // method to make output of toString method more pretty
    public String formatDate(Calendar date) {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("dd.MM.yyyy HH:mm");
        return sdf.format(date.getTime());
    }
}
