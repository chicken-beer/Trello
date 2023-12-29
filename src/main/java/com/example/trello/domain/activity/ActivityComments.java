package com.example.trello.domain.activity;

public class ActivityComments {

    public String ADDUSER(String addingUser, String addedUser) {
        return addingUser+"가 "+addedUser+"를 멤버에 추가했습니다.";
    }

    public String DELETEUSER(String addingUser, String addedUser) {
        return addingUser+"가 "+addedUser+"를 멤버에서 삭제했습니다.";
    }
}
