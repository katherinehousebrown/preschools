package org.launchcode.preschools.models.forms.Admin;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
public class SchoolInfo {

    @Id
    @GeneratedValue
    int id;

    @NotNull
    Integer ratio;

    @NotNull
    String pottyTrained;

    @NotNull
    Integer hours;

    @NotNull
    Double tuition;

    //create one to one mapping for Address and SchoolInfo
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER) //cascade ALL: if merged, persist, refresh, or deleted one row, same applied to corresponding row on other table
    @JoinColumn(name="address_FK")           //EAGER loads both sets of data together for each instance
    public Address address;


    //constructor
    public SchoolInfo(Integer ratio, String pottyTrained, Integer hours, Double tuition)
    {
        this.ratio = ratio;
        this.pottyTrained = pottyTrained;
        this.hours = hours;
        this.tuition = tuition;
    }

    //for hibernate
    public SchoolInfo(){}

    //getters and setters
    public int getId() {
        return id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Integer getRatio() {
        return ratio;
    }

    public void setRatio(Integer ratio) {
        this.ratio = ratio;
    }

    public String getPottyTrained() {
        return pottyTrained;
    }

    public void setPottyTrained(String pottyTrained) {
        this.pottyTrained = pottyTrained;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public Double getTuition() {
        return tuition;
    }

    public void setTuition(Double tuition) {
        this.tuition = tuition;
    }
}
