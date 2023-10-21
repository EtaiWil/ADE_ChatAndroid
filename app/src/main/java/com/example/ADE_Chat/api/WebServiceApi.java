package com.example.ADE_Chat.api;

import com.example.ADE_Chat.entities.ContactClass;
import com.example.ADE_Chat.entities.LoginFields;
import com.example.ADE_Chat.entities.MessageClass;
import com.example.ADE_Chat.entities.RegisterUser;
import com.example.ADE_Chat.entities.contactFields;
import com.example.ADE_Chat.entities.msgFields;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WebServiceApi {
    @GET("Chats")
    Call<List<ContactClass>> getContacts(@Header("Authorization") String token);

    @POST("Chats")
    Call<Void> addContact(@Body contactFields contact, @Header("Authorization") String token);

    @POST("Tokens")
    Call<String> login(@Body LoginFields loginFields);

    @POST("Users")
    Call<String> register(@Body RegisterUser user);

    @GET("Chats/{id}/Messages/")
    Call<List<MessageClass>> getMessages(@Path("id") String id, @Header("Authorization") String token);

    @POST("Chats/{id}/Messages/")
    Call<Void> addMessage(@Path("id") String id, @Body msgFields msgFields1, @Header("Authorization") String token);

   
}
