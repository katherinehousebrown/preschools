package org.launchcode.preschools.models.forms.User;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class UserSearch {

    @Id
    @GeneratedValue
    int id;

    @NotNull
    String searchForm;

    //constructor
    public UserSearch(String searchForm)
    {
        this.searchForm = searchForm;
    }

    //getters and setters
    public String getSearchForm() {
        return searchForm;
    }

    public void setSearchForm(String searchForm) {
        this.searchForm = searchForm;
    }
}
