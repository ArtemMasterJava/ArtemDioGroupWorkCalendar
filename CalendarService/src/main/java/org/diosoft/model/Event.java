package org.diosoft.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

public class Event implements Serializable {
    private final String title;
    private final String description;
    private final UUID id;
    private final List<Person> attendees;
    private final GregorianCalendar startDate;
    private final GregorianCalendar endDate;

    private Event(Builder builder) {
        this.title = builder.title;
        this.description = builder.description;
        this.id = builder.id;
        this.attendees = builder.attendees;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public UUID getId() {
        return id;
    }

    public List<Person> getAttendees() {
        return attendees;
    }

    public GregorianCalendar getStartDate() {
        return startDate;
    }

    public GregorianCalendar getEndDate() {
        return endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        if (title != null ? !title.equals(event.title) : event.title != null) return false;
        if (description != null ? !description.equals(event.description) : event.description != null) return false;
        if (id != null ? !id.equals(event.id) : event.id != null) return false;
        if (attendees != null ? !attendees.equals(event.attendees) : event.attendees != null) return false;
        if (startDate != null ? !startDate.equals(event.startDate) : event.startDate != null) return false;
        return !(endDate != null ? !endDate.equals(event.endDate) : event.endDate != null);

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

    public static class Builder {
        private String title;
        private String description;
        private UUID id;
        private List<Person> attendees;
        private GregorianCalendar startDate;
        private GregorianCalendar endDate;

        public Builder() {
        }

        public Builder(Event original) {
            this.title = original.title;
            this.description = original.description;
            this.id = original.id;
            this.attendees = original.attendees;
            this.startDate = original.startDate;
            this.endDate = original.endDate;
        }

        public Builder title(String value) {
            this.title = value;
            return this;
        }

        public Builder description(String value) {
            this.description = value;
            return this;
        }

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder attendees(List<Person> value) {
            this.attendees = value;
            return this;
        }

        public Builder startDate(GregorianCalendar value) {
            this.startDate = value;
            return this;
        }

        public  Builder endDate(GregorianCalendar value) {
            this.endDate = value;
            return this;
        }

        public Event build() {
            return new Event(this);
        }
    }

    // method to make output of toString method more pretty
    public String formatDate(Calendar date) {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("dd.MM.yyyy HH:mm");
        return sdf.format(date.getTime());
    }
}
