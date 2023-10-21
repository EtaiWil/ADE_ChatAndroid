package com.example.ADE_Chat;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.ADE_Chat.daos.ContactDao;
import com.example.ADE_Chat.daos.MessageDao;
import com.example.ADE_Chat.daos.UserDao;
import com.example.ADE_Chat.entities.Contact;
import com.example.ADE_Chat.entities.Message;
import com.example.ADE_Chat.entities.User;

@Database(entities = {Contact.class, Message.class, User.class}, version = 4)
public abstract class AppDB extends RoomDatabase{
    public abstract MessageDao messageDao();
    public abstract ContactDao contactDao();
    public abstract UserDao userDao();
    private static AppDB INSTANCE;

    public static AppDB getDBInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(), AppDB.class, "localDB")
                    .allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }
        return INSTANCE;
    }
}
