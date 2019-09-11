package org.launchcode.preschools.models.forms.Admin;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigInteger;
import java.security.MessageDigest;

@Entity
public class Admin {

    @Id
    @GeneratedValue
    private int id;

    private String userName;
    private String password;

    //constructor
    public Admin(String userName, String password){
        this.userName = userName;
        this.password = password;
    }

    public Admin(){}

    //getters and setters
    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
