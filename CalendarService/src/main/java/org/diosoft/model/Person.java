package org.diosoft.model;

import java.io.Serializable;

public class Person implements Serializable {
    final private String firstName;
    final private String lastName;
    final private String email;

    private Person(Builder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.email = builder.email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (firstName != null ? !firstName.equals(person.firstName) : person.firstName != null) return false;
        if (lastName != null ? !lastName.equals(person.lastName) : person.lastName != null) return false;
        return !(email != null ? !email.equals(person.email) : person.email != null);

    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append(firstName).append(" ").append(lastName).append(" ");
        sb.append(", email: ").append(email).append("}");
        return sb.toString();
    }

    public static class Builder {
        private String firstName;
        private String lastName;
        private String email;

        public Builder() {}

        public Builder(Person original) {
            this.firstName = original.firstName;
            this.lastName = original.lastName;
            this.email = original.email;

        }

        public Builder firstName(String value) {
            this.firstName = value;
            return this;
        }

        public Builder lastName(String value) {
            this.lastName = value;
            return this;
        }

        public Builder email(String value) {
            this.email = value;
            return this;
        }

        public Person build() {
            return new Person(this);
        }


    }

}
