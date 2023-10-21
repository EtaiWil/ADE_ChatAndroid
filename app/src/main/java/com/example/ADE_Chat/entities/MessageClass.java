package com.example.ADE_Chat.entities;

public class MessageClass {
    private String id;
    private String content;
    private String created;
    private boolean sent;
    private Sender sender;
    public MessageClass() {}

    public MessageClass(String content, String created,boolean sent, String contactName) {
        this.content = content;
        this.created = created;
        this.sent=sent;
        this.contactName = contactName;
    }

    private String contactName;

    public Sender getSender() {
        return sender;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public boolean getSent() {
        return sent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
    public static class Sender {
        private String username;


        public Sender(String username) {
            this.username = username;

        }
// Getters and setters for LastMessage properties


        public void setUsername(String username) {
            this.username = username;
        }

        public String getUsername() {
            return username;
        }
    }
}
