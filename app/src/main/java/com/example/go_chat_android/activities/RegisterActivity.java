package com.example.go_chat_android.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.room.Room;

import com.example.go_chat_android.AppDB;
import com.example.go_chat_android.MyApplication;
import com.example.go_chat_android.api.WebServiceApi;
import com.example.go_chat_android.daos.UserDao;
import com.example.go_chat_android.databinding.ActivityRegisterBinding;
import com.example.go_chat_android.entities.RegisterUser;
import com.example.go_chat_android.entities.User;
import com.example.go_chat_android.lists.ContactList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {
   private String base64Image;

    private AppDB db;
    private ActivityRegisterBinding registerBinding;
    private final int GALLERY_REQ_CODE = 1000;
    private Retrofit retrofit;
    private WebServiceApi webServiceApi;
    private Gson gson;
    private UserDao userDao;
    private byte[] byteImage;
    private ImageView imageView;
    private Bitmap image;

    private static String base64String;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerBinding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(registerBinding.getRoot());
        Log.d("ferdous", "Lunch Time");
        db = Room.databaseBuilder(getApplicationContext(), AppDB.class, "UsersDB").allowMainThreadQueries().build();
        userDao = db.userDao();

        registerBinding.btnRegister.setOnClickListener(v -> {
            String password = registerBinding.etRegisterPassword.getText().toString();
            TextView tvError = registerBinding.tvRegisterError;
            tvError.setText("");
//            String regEx = "^(?:(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).*)[^\\s]{8,}$";

            if (!password.equals(registerBinding.etRegisterAgain.getText().toString())) {
                tvError.setText("The passwords are different!");
                tvError.setVisibility(View.VISIBLE);
                return;
            }

            if (password.length() < 8) {
                tvError.setText("The password must be 8 characters or longer!");
                tvError.setVisibility(View.VISIBLE);
                return;
            }

            if (!Pattern.matches(".*[0-9].*", password)) {
                tvError.setText("The password must contain at least 1 numeric character!");
                tvError.setVisibility(View.VISIBLE);
                return;
            }

            if (!Pattern.matches(".*[a-z].*", password)) {
                tvError.setText("The password must contain at least 1 n lowercase character!");
                tvError.setVisibility(View.VISIBLE);
                return;
            }

            if (!Pattern.matches(".*[A-Z].*", password)) {
                tvError.setText("The password must contain at least 1 uppercase character!");
                tvError.setVisibility(View.VISIBLE);
                return;
            }

        

            String username = registerBinding.etRegisterUsername.getText().toString();
            if (!Pattern.matches("[A-Za-z0-9 _-]{3,30}$", username)) {
                tvError.setText("Invalid username!");
                tvError.setVisibility(View.VISIBLE);
                return;
            }

            String nickname = registerBinding.etRegisterNickname.getText().toString();
            if (!Pattern.matches("[A-Za-z0-9 _-]{3,30}$", nickname)) {
                tvError.setText("Invalid nickname!");
                tvError.setVisibility(View.VISIBLE);
                return;
            }

            tvError.setVisibility(View.INVISIBLE);
            new Thread(() -> {
                RegisterUser user = new RegisterUser(username, password, nickname, base64Image, null, MyApplication.BaseUrl);
                gson = new GsonBuilder()
                        .setLenient()
                        .create();
                retrofit = new Retrofit.Builder()
                        .baseUrl(MyApplication.BaseUrl)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();
                webServiceApi = retrofit.create(WebServiceApi.class);
                Call<String> call = webServiceApi.register(user);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        runOnUiThread(() -> {
                            Log.d("ferdous", "entering the function");
                            if (response.isSuccessful()) {
                                Log.d("ferdous", "response is successful");
                                MyApplication.token = response.body();
                                MyApplication.username = username;

                                User u = new User(username, nickname, byteImage);
                                userDao.insert(u);
                                finish();
                                Intent loginIntent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(loginIntent);
                            } else {

                                    tvError.setText("user already exist!");
                                     tvError.setVisibility(View.VISIBLE);

                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        System.out.println("tomer ben sharmuta mizdayen hocel xhataht");;
                        // Handle failure here
                    }
                });
            }).start();


        });


            registerBinding.btnAddImage.setOnClickListener(v -> {
                  if (ContextCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                          PackageManager.PERMISSION_GRANTED) {
                      ActivityCompat.requestPermissions(RegisterActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
                  } else {
                      selectImage();
                  }
              });
          }



   @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQ_CODE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] bytes = stream.toByteArray();
                base64String = Base64.encodeToString(bytes, Base64.NO_WRAP);
                base64Image ="data:image/jpeg;base64,"+base64String;
            /*  *//*  base64Image=base64Image+"==";*//*
                Log.d("TAG",  base64Image);*/

                byteImage = bytes; // Save the byte array for later use if needed
            } catch (IOException e) {
                // Handle the exception appropriately
                e.printStackTrace();
            }
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            selectImage();
        } else {
            Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }
     private void selectImage() {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "Select Image"), GALLERY_REQ_CODE);
        }


}