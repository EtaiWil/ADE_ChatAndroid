package com.example.go_chat_android.entities;

import java.util.ArrayList;
import java.util.List;

public class RegisterUser {
    private String username;
    private String password;
    private String displayName;
    private String profilePic;
    private List<Contact> Contacts;
    private String Connection;

    public RegisterUser(String Username, String Password, String NickName,  String profilePic,Contact Contacts, String Connection) {
        this.username = Username;
        this.password = Password;
        this.displayName = NickName;
        this.profilePic = profilePic;
        this.Connection = Connection;
        this.Contacts = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }



    public String getNickName() {
        return displayName;
    }

    public String getPhoto() {
        return profilePic;
    }

    public List<Contact> getContacts() {
        return Contacts;
    }

    public String getConnection() {
        return Connection;
    }
}
