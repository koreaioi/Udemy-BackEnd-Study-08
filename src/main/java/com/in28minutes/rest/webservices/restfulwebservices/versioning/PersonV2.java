package com.in28minutes.rest.webservices.restfulwebservices.versioning;

public class PersonV2 {
    private String firstName;
    private String lastName;

    public PersonV2(String firstName, String lastName) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public String toString() {
        return "PersonV2{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
