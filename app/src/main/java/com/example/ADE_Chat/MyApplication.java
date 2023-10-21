package com.example.ADE_Chat;

import android.app.Application;
import android.content.Context;

import com.example.ADE_Chat.daos.ContactDao;
import com.example.ADE_Chat.daos.UserDao;

public class MyApplication extends Application {
    public static Context context;
    public static String BaseUrl;

    public static String token;
    public static String username;
    public static String friendBaseurl;
    public static UserDao userDao;
    public static ContactDao contactDao;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        BaseUrl = "http://10.0.2.2:5000/api/";
    }
}
