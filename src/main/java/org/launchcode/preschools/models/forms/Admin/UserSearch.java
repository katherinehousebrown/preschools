package org.launchcode.preschools.models.forms.Admin;


import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;


public class UserSearch {

    @Id
    @GeneratedValue
    int id;

    @NotNull
    String searchResults;

    //constructor
    public UserSearch(String searchResults)
    {
        this.searchResults = searchResults;
    }

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
