package com.example.go_chat_android.api;

import com.example.go_chat_android.entities.ContactClass;
import com.example.go_chat_android.entities.LoginFields;
import com.example.go_chat_android.entities.MessageClass;
import com.example.go_chat_android.entities.RegisterUser;
import com.example.go_chat_android.entities.contactFields;
import com.example.go_chat_android.entities.msgFields;

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
