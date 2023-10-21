package com.example.ADE_Chat.lists;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.ADE_Chat.AppDB;
import com.example.ADE_Chat.MyApplication;
import com.example.ADE_Chat.R;
import com.example.ADE_Chat.adapters.MessageListAdapter;
import com.example.ADE_Chat.api.WebServiceApi;
import com.example.ADE_Chat.daos.ContactDao;
import com.example.ADE_Chat.daos.MessageDao;
import com.example.ADE_Chat.entities.Contact;
import com.example.ADE_Chat.entities.Message;
import com.example.ADE_Chat.entities.MessageClass;
import com.example.ADE_Chat.entities.msgFields;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MessageList extends AppCompatActivity {
    private AppDB db;
    private AppDB db2;
    private MessageDao messageDao;
    private ContactDao contactDao;
    private List<Message> messageList;
    private String contactName;
    private String contactID;
    private String chatId;
    private EditText etInput;
    private Button btnSend;
    private TextView tvName;
    private RecyclerView RVMessageList;
    private SwipeRefreshLayout swipeContainer;
    private MessageListAdapter adapter;
    private Retrofit retrofit;
    private WebServiceApi webServiceApi;
    private Gson gson;
    private String token;
    private String sImage;
    private String displayName;
    private  ImageView imageView;

    public MessageList() {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);

        db = Room.databaseBuilder(getApplicationContext(), AppDB.class, "MessagesDB").allowMainThreadQueries().build();
        db2 = Room.databaseBuilder(getApplicationContext(), AppDB.class, "UsersDB").allowMainThreadQueries().build();
        messageDao = db.messageDao();
        contactDao = db2.contactDao();
        sImage=getIntent().getExtras().getString("picture");
        displayName=getIntent().getExtras().getString("displayName");
        contactName = getIntent().getExtras().getString("contactName");
        MyApplication.friendBaseurl = getIntent().getExtras().getString("url");
        contactID = getIntent().getExtras().getString("contactID");
        chatId = getIntent().getExtras().getString("chatId");
        tvName = findViewById(R.id.tvFriendNickname);
        tvName.setText(displayName);
        String base64Data = sImage.substring(sImage.indexOf(",") + 1);
        messageList = new ArrayList<>();

        RVMessageList = findViewById(R.id.lvMessageList);
        adapter = new MessageListAdapter(this);
        RVMessageList.setAdapter(adapter);
        RVMessageList.setLayoutManager(new LinearLayoutManager(this));
        adapter.setMessageList(messageList);

        btnSend = findViewById(R.id.btnSend);
        etInput = findViewById(R.id.etTextInput);
        imageView = findViewById(R.id.ivFriendPic);

        byte[] decodedBytes = Base64.decode(base64Data, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
        imageView.setImageBitmap(bitmap);

        gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.BaseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        webServiceApi = retrofit.create(WebServiceApi.class);
        token = MyApplication.token;

        new Thread(() -> {
            Call<List<MessageClass>> call = webServiceApi.getMessages(chatId, "Bearer " + token);
            call.enqueue(new Callback<List<MessageClass>>() {
                @Override
                public void onResponse(Call<List<MessageClass>> call, Response<List<MessageClass>> response) {
                    if (response.isSuccessful()) {
                        for (Message msg : messageList) {
                            messageDao.delete(msg);
                        }
                        MessageClass last = null;
                        List<MessageClass> List = response.body();
                        for (MessageClass msg : List) {
                             msg.setContactName(contactName);

                            Message message = new Message(msg, MyApplication.username);
                            messageDao.insert(message);
                            last = msg;
                        }

                        if (last != null) {

                        }
                        onResume();
                    }
                }

                @Override
                public void onFailure(Call<List<MessageClass>> call, Throwable t) {

                }
            });
        }).start();


        btnSend.setOnClickListener(v -> {
            String content = etInput.getText().toString();
            if (etInput.length() != 0) {
                Date date = Calendar.getInstance().getTime();
                DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy hh:mm:ss");
                String strDate = dateFormat.format(date);
                Message message = new Message(content, strDate, true, contactName);
                message.setUserId(MyApplication.username);
                messageDao.insert(message);
                onResume();
                RVMessageList.scrollToPosition(messageList.size() - 1);
                etInput.setText("");
                new Thread(() -> {
                    msgFields msgFields1 = new msgFields(content);
                    Call<Void> call = webServiceApi.addMessage( chatId,msgFields1,"Bearer " + token);
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                           List<Contact> contactList=new ArrayList<>();
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {

                        }
                    });
                }).start();


            }
        });
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.messageRefreshLayout);
        swipeContainer.setOnRefreshListener(() ->
                new Thread(() -> {
                    Call<List<MessageClass>> call = webServiceApi.getMessages(chatId, "Bearer " + token);
                    call.enqueue(new Callback<List<MessageClass>>() {
                        @Override
                        public void onResponse(Call<List<MessageClass>> call, Response<List<MessageClass>> response) {
                            if (response.isSuccessful()) {
                                for (Message msg : messageList) {
                                    messageDao.delete(msg);
                                }
                                MessageClass last = null;
                                List<MessageClass> List = response.body();
                                for (MessageClass msg : List) {
                                    msg.setContactName(contactName);
                                    Message message = new Message(msg, MyApplication.username);
                                    messageDao.insert(message);
                                    last = msg;
                                }
                                swipeContainer.setRefreshing(false);

                                if (last != null) {

                                }
                                onResume();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<MessageClass>> call, Throwable t) {
                            swipeContainer.setRefreshing(false);
                        }
                    });
                }).start());

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onResume() {
        super.onResume();
        messageList.clear();
        messageList.addAll(messageDao.get(contactName, MyApplication.username));
        adapter.notifyDataSetChanged();
    }


    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver((mMessageReceiver),
                new IntentFilter("MyData"));
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            new Thread(() -> {
                Call<List<MessageClass>> call = webServiceApi.getMessages(contactName, "Bearer " + token);
                call.enqueue(new Callback<List<MessageClass>>() {
                    @Override
                    public void onResponse(Call<List<MessageClass>> call, Response<List<MessageClass>> response) {
                        if (response.isSuccessful()) {
                            for (Message msg : messageList) {
                                messageDao.delete(msg);
                            }
                            MessageClass last = null;
                            List<MessageClass> List = response.body();
                            for (MessageClass msg : List) {
                                msg.setContactName(contactName);
                                Message message = new Message(msg, MyApplication.username);
                                messageDao.insert(message);
                                last = msg;
                            }


                            if (last != null) {

                            }
                            onResume();
                            RVMessageList.scrollToPosition(messageList.size() - 1);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<MessageClass>> call, Throwable t) {

                    }
                });
            }).start();
        }
    };
}