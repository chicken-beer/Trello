package com.example.trello.domain.activity.service;

public class ActivityContents {

    public String ADDUSER(String addingUser, String addedUser) {
        return addingUser+" added "+addedUser+" to this card";
    }

    public String ADDUSERSELF(String addedUser) {
        return addedUser+"Joined Card";
    }

    public String DELETEUSER(String addingUser, String addedUser) {
        return addingUser+" removed "+addedUser+" from this card";
    }

    public String DELETEUSERSELF(String addedUser) {
        return addedUser+" left this card";
    }
}
