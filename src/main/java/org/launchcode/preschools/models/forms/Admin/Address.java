package org.launchcode.preschools.models.forms.Admin;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Address {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    String name;

    @NotNull
    String address;

    @NotNull
    String city;

    @NotNull
    String state;

    @NotNull
    Integer zipCode;

    @NotNull
    String website;

    //create one to one mapping for Address and SchoolInfo
    @OneToOne(cascade = CascadeType.ALL, fetch=FetchType.EAGER) //cascade ALL: if merged, persist, refresh, or deleted one row, same applied to corresponding row on other table
    @JoinColumn(name="schoolInfo_FK") //name column in table FK=foreign key
    private SchoolInfo schoolInfo;

    //constructors
    public Address(String name, String address, String city, String state,
            Integer zipCode, String website)
    {
        this.name = name;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.website = website;
    }

    //for hibernate
    public Address(){}

    //getters and setters


    public SchoolInfo getSchoolInfo() {
        return schoolInfo;
    }

    public void setSchoolInfo(SchoolInfo schoolInfo) {
        this.schoolInfo = schoolInfo;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getAddress1() {
        return address;
    }

    public void setAddress1(String address1) {
        this.address = address1;
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

    public String getEmail() {
        return address;
    }

    public void setEmail(String email) {
        this.address = email;
    }
}
