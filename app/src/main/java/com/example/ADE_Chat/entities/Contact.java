package com.example.ADE_Chat.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "contactsTable")
public class Contact {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String userId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    private String name;
    private String nickname;
    private String server;
    private String last;
    private String lastdate;
    private String chatId;
     private String profilePic;

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public Contact(String name, String nickname, String server, String last, String lastdate,String chatId,String profilePic ) {
        this.nickname = nickname;
        this.name = name;
        this.server = server;
        this.last = last;
        this.lastdate = lastdate;
        this.chatId=chatId;
        this.profilePic=profilePic;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public String getChatId() {
        return chatId;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getLastdate() {
        return lastdate;
    }

    public void setLastdate(String lastdate) {
        this.lastdate = lastdate;
    }

    public String getNickname() {
        return nickname;
    }
}