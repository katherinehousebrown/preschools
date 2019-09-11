package org.launchcode.preschools.models.forms.User;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class UserSearch {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private String searchResults;

    //constructor
    public UserSearch(String searchResults)
    {
        this.searchResults = searchResults;
    }

    public UserSearch(){}

    //getters and setters
    public int getId() {
        return id;
    }

    public String getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(String searchResults) {
        this.searchResults = searchResults;
    }
}
