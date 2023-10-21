package com.example.go_chat_android.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "messagesTable")
public class Message {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String content;
    private String created;
    private Boolean sent;

    public Message() {}

    public Message(String content, String created, Boolean sent, String contactName) {
        this.content = content;
        this.created = created;
        this.sent = sent;
        this.contactName = contactName;
    }

    public Message(MessageClass msg, String userId) {
        this.content = msg.getContent();
        this.created = msg.getCreated();
        this.contactName = msg.getContactName();
        this.userId = userId;
        //check if i sent the message or got it
        if(msg.getSender().getUsername().equals(userId)){
            this.sent=true;
        }
        else {
            this.sent=false;
        }
    }

    private String userId;
    private String contactName;

    public String getUserId()
    {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public Boolean getSent() {
        return sent;
    }

    public void setSent(Boolean sent) {
        this.sent = sent;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
}
