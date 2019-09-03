package org.launchcode.preschools.models.forms.Admin;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
public class SchoolInfo {
    @Id
    @GeneratedValue
    @Column(name="schoolInfo_FK") //for one to one table readability
    int id;

    @NotNull
    Integer ratio;

    @NotNull
    String pottyTrained;

    @NotNull
    Integer hours;

    @NotNull
    Double tuition;

//    @OneToOne(mappedBy = "schoolInfo")
//    private Address address;             // do I need this for one to one ???????

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

    public void setId(int id) {
        this.id = id;
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
