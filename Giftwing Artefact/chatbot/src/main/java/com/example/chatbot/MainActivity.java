package com.example.chatbot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

        private RecyclerView chatsRV;
        private EditText userMsgEdt;
        private FloatingActionButton sendMsgFAB;
        private final String BOT_KEY = "bot";
        private final String USER_KEY = "user";
        private ArrayList<Chatsmdl>chatsmdlArrayList;
        private ChatRVAdapter chatRVAdapter;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            chatsRV = findViewById(R.id.idRVChats);
            userMsgEdt = findViewById(R.id.idEdtMessagae);
            sendMsgFAB = findViewById(R.id.idFABSend);
            chatsmdlArrayList = new ArrayList<>();
            chatRVAdapter = new ChatRVAdapter(chatsmdlArrayList,this);
            LinearLayoutManager manager = new LinearLayoutManager(this);
            chatsRV.setLayoutManager(manager);
            chatsRV.setAdapter(chatRVAdapter);

            sendMsgFAB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                        if (userMsgEdt.getText().toString().isEmpty()){
                            Toast.makeText(MainActivity.this, "Please Enter your message", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    getResponse(userMsgEdt.getText().toString());
                    userMsgEdt.setText("");
                }
            });

        }
        @SuppressLint("NotifyDataSetChanged")
        private void getResponse(String message){
            chatsmdlArrayList.add(new Chatsmdl(message,USER_KEY));
            chatRVAdapter.notifyDataSetChanged();
            String url = "http://api.brainshop.ai/get?bid=174662&key=zg51PzIlsD1gzSxo&uid=[uid]&msg="+message;
            String BASE_URL = "http://api.brainshop.ai/";
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
            Call<Msgmdl> call = retrofitAPI.getMessage(url);
            call.enqueue(new Callback<Msgmdl>() {
                @Override
                public void onResponse(@NonNull Call<Msgmdl> call, @NonNull Response<Msgmdl> response) {
                    if(response.isSuccessful()){
                        Msgmdl mdl = response.body();
                        assert mdl != null;
                        chatsmdlArrayList.add(new Chatsmdl(mdl.getCnt (),BOT_KEY));
                        chatRVAdapter.notifyDataSetChanged();
                    }

                }

                @Override
                public void onFailure(@NonNull Call<Msgmdl> call, @NonNull Throwable t) {
                    chatsmdlArrayList.add(new Chatsmdl("Something went wrong!",BOT_KEY));
                    chatRVAdapter.notifyDataSetChanged();
                }
            });
        }
}