package com.example.ADE_Chat.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
//
//public class ContactClass {
//    private String id;
//    private String name;
//    private String server;
//    private String last;
//    private String lastdate;
//    private List<MessageClass> messages;
//
//    public ContactClass(String name, String server) {
//        this.name = name;
//        this.server = server;
//        this.last = null;
//        Date date = Calendar.getInstance().getTime();
//        DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy hh:mm:ss");
//        String strDate = dateFormat.format(date);
//        this.lastdate = strDate;
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public String getServer() {
//        return server;
//    }
//
//    public void setServer(String server) {
//        this.server = server;
//    }
//
//    public String getLast() {
//        return last;
//    }
//
//    public void setLast(String last) {
//        this.last = last;
//    }
//
//    public String getLastdate() {
//        return lastdate;
//    }
//
//    public void setLastdate(String lastdate) {
//        this.lastdate = lastdate;
//    }
//
//}


@Entity
public class ContactClass {
    @PrimaryKey
    private String id;
    private User user;
    private LastMessage lastMessage;


    private String name;
    private String server;
    private String last;
    private String lastdate;
    private List<MessageClass> messages;

    public ContactClass(String id, User user, LastMessage lastMessage) {
        this.id = id;
        this.user = user;
        this.lastMessage = lastMessage;
    }

    public ContactClass(String name, String server) {
        this.name = name;
        this.server = server;
        this.last = null;
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy hh:mm:ss");
        String strDate = dateFormat.format(date);
        this.lastdate = strDate;
        // Initialize other properties as needed
    }

    // Getters and setters for existing properties

    public String getServer() {
        return server;
    }

    public String getName() {
        return name;
    }

    public String getLast() {
        return last;
    }

    public String getLastdate() {
        return lastdate;
    }

    public List<MessageClass> getMessages() {
        return messages;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LastMessage getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(LastMessage lastMessage) {
        this.lastMessage = lastMessage;
    }

    // Inner classes for "User" and "LastMessage"
    public static class User {
        private String username;
        private String displayName;

        public User(String username, String displayName, String profilePic) {
            this.username = username;
            this.displayName = displayName;
            this.profilePic = profilePic;
        }

        private String profilePic;

        // Getters and setters for User properties

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public String getProfilePic() {
            return profilePic;
        }

        public void setProfilePic(String profilePic) {
            this.profilePic = profilePic;
        }
    }

    public static class LastMessage {
        private String id;
        private String created;
        private String content;

        public LastMessage(String id, String created, String content) {
            this.id = id;
            this.created = created;
            this.content = content;
        }
// Getters and setters for LastMessage properties

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}