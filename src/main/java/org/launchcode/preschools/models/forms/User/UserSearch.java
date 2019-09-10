package org.launchcode.preschools.models.forms.User;

import javax.validation.constraints.NotNull;

public class UserSearch {
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
