package org.launchcode.preschools.models.forms.Admin;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
public class Address {

    @Id
    @GeneratedValue
    @Column(name="address_FK") //for one to one table readability
    private int id;

    @NotNull
    String name;

    @NotNull
    String address1;

    @NotNull
    String city;

    @NotNull
    String state;

    @NotNull
    Integer zipCode;

    @NotNull
    String website;



    //constructors
    public Address(String name, String address1, String city, String state,
            Integer zipCode, String website)
    {
        this.name = name;
        this.address1 = address1;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.website = website;
    }

    //for hibernate
    public Address(){}

    //getters and setters


    public void setId(int id) {
        this.id = id;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getZipCode() {
        return zipCode;
    }

    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
