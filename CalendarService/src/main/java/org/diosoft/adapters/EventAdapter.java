package org.diosoft.adapters;

import org.diosoft.model.Event;
import org.diosoft.model.Person;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.text.SimpleDateFormat;
import java.util.*;

@XmlRootElement(name = "event")
@XmlType(propOrder={"id", "title", "description", "startDate", "endDate", "attendees"})
public class EventAdapter {
    private String title;
    private String description;
    private UUID id;
    private List<PersonAdapter> attendees;
    private GregorianCalendar startDate;
    private GregorianCalendar endDate;

    public EventAdapter() {}

    public EventAdapter(Event event) {
        this.title = event.getTitle();
        this.description = event.getDescription();
        this.id = event.getId();
        this.attendees = new ArrayList<>();
        if (event.getAttendees() != null ) {
            for (Person person : event.getAttendees()) {
                this.attendees.add(new PersonAdapter(person));
            }
        }
        this.startDate = event.getStartDate();
        this.endDate = event.getEndDate();
    }

    public String getTitle() {
        return title;
    }

    @XmlElement
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    @XmlElement
    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getId() {
        return id;
    }

    @XmlElement
    public void setId(UUID id) {
        this.id = id;
    }

    public List<PersonAdapter> getAttendees() {
        return attendees;
    }

    @XmlElementWrapper(name="attendees")
    @XmlElement(name = "person")
    public void setAttendees(List<PersonAdapter> attendees) {
        this.attendees = attendees;
    }

    public GregorianCalendar getStartDate() {
        return startDate;
    }

    @XmlElement
    public void setStartDate(GregorianCalendar startDate) {
        this.startDate = startDate;
    }

    public GregorianCalendar getEndDate() {
        return endDate;
    }

    @XmlElement
    public void setEndDate(GregorianCalendar endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventAdapter that = (EventAdapter) o;

        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (attendees != null ? !attendees.equals(that.attendees) : that.attendees != null) return false;
        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) return false;
        return !(endDate != null ? !endDate.equals(that.endDate) : that.endDate != null);

    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (attendees != null ? attendees.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Event").append("\n");
        sb.append("title: ").append(title).append("\n");
        sb.append("description: ").append(description).append("\n");
        sb.append("id: ").append(id).append("\n");
        sb.append("attendees: ").append(attendees).append("\n");
        sb.append("startDate: ").append(formatDate(startDate)).append("\n");
        sb.append("endDate: ").append(formatDate(endDate)).append("\n");
        sb.append("\n");
        return sb.toString();
    }

    // method to make output of toString method more pretty
    public String formatDate(Calendar date) {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("dd.MM.yyyy HH:mm");
        return sdf.format(date.getTime());
    }
}
