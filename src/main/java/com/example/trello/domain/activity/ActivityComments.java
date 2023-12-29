package com.example.trello.domain.activity;

public class ActivityComments {

    public String ADDUSER(String addingUser, String addedUser) {
        return addingUser+" added "+addedUser+" to this card";
    }

    public String ADDSELF(String addedUser) {
        return addedUser+"Joined Card";
    }

    public String DELETEUSER(String addingUser, String addedUser) {
        return addingUser+" removed "+addedUser+" from this card";
    }

    public String DELETESELF(String addedUser) {
        return addedUser+" left this card";
    }
}
