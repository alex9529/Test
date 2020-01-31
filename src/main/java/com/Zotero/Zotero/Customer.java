package com.Zotero.Zotero;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


public class Customer {


    private Long id;
    private String firstname;
    private String lastName;

    protected Customer() {}

    public Customer(String firstname, String lastName) {
        this.firstname = firstname;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return String.format(
                "Customer[id=%d, firstName='%s', lastName='%s']",
                id, firstname, lastName);
    }

    public Long getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastName() {
        return lastName;
    }
}
