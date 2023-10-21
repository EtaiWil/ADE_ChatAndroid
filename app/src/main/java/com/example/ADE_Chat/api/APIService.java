package com.example.ADE_Chat.api;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.ADE_Chat.MyApplication;
import com.example.ADE_Chat.daos.ContactDao;
import com.example.ADE_Chat.entities.Contact;

import com.example.ADE_Chat.entities.LoginFields;
import com.example.ADE_Chat.entities.MessageClass;
import com.example.ADE_Chat.entities.RegisterUser;
import com.example.ADE_Chat.entities.contactFields;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIService {
    private MutableLiveData<List<Contact>> contactListData;
    private ContactDao dao;
    Retrofit retrofit;
    WebServiceApi webServiceApi;
    Gson gson;

    public APIService() {
        gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.BaseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        webServiceApi = retrofit.create(WebServiceApi.class);

    }



    public void login(LoginFields loginFields) {
        Call<String> call = webServiceApi.login(loginFields);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("tag","the respnese is"+response);
                if (response.isSuccessful()) {
                    MyApplication.token = response.body();
                } else {

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                String tmp = "";
            }
        });
    }

    public void register(RegisterUser user) {
        Call<String> call = webServiceApi.register(user);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    MyApplication.token = response.body();
                } else {

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });
    }

    public void getMessages(String contact, String token) {
        Call<List<MessageClass>> call = webServiceApi.getMessages(contact,"Bearer " + token);
        call.enqueue(new Callback<List<MessageClass>>() {
            @Override
            public void onResponse(Call<List<MessageClass>> call, Response<List<MessageClass>> response) {
                if (response.isSuccessful()) {
                    List<MessageClass> messages = response.body();
                }
            }

            @Override
            public void onFailure(Call<List<MessageClass>> call, Throwable t) {

            }
        });
    }



    public void addContact(contactFields contactFields, String token) {
        Call<Void> call = webServiceApi.addContact(contactFields, "Bearer " + token);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}
