package dev.mah.nassa.chat_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import dev.mah.nassa.chat_app.DataBase.RealTime_Base;
import dev.mah.nassa.chat_app.Modles.Message;
import dev.mah.nassa.chat_app.Modles.MessageAdapter;
import dev.mah.nassa.chat_app.Modles.Message_MVVM;
import dev.mah.nassa.chat_app.Modles.ShowUsersAdapter;
import dev.mah.nassa.chat_app.Modles.Users;

public class Chat_Activity extends AppCompatActivity{

    CircularImageView circularImageView;
    TextView userName;
    Button sendButton;
    EditText editText;
    RecyclerView chatRecycler;
    Users users;
    MessageAdapter messageAdapter;
    DatabaseReference databaseReferenceSender , databaseReferenceReciver ;
    String recivedId;
    String senderRoom , reciverRoom;
    Message message;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        circularImageView = findViewById(R.id.profile_iv_image);
        userName = findViewById(R.id.userName);
        sendButton = findViewById(R.id.sendButton);
        editText = findViewById(R.id.messageEditText);
        chatRecycler = findViewById(R.id.messageRecyclerView);


        // Get the Users object from Intent
        Intent intent = getIntent();
        users = (Users) intent.getSerializableExtra("obj");
        userName.setText(users.getName());
        recivedId=users.getId();

        senderRoom = loadUid()+recivedId;
        reciverRoom = recivedId+loadUid();

        //Adapter
        messageAdapter = new MessageAdapter(Chat_Activity.this , loadUid());
        chatRecycler.setAdapter(messageAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(Chat_Activity.this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        layoutManager.setReverseLayout(true); // Set reverse layout
        chatRecycler.setLayoutManager(layoutManager);
        chatRecycler.scrollToPosition(messageAdapter.getItemCount() - 1);


        databaseReferenceSender = FirebaseDatabase.getInstance().getReference("chats").child(senderRoom);
        databaseReferenceReciver = FirebaseDatabase.getInstance().getReference("chats").child(reciverRoom);
        databaseReferenceSender.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageAdapter.clearAdapter();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    message = dataSnapshot.getValue(Message.class);
                    messageAdapter.addMessage(message);
                    chatRecycler.scrollToPosition(0);
                }
//                if(message.getRecipientId().equals(loadUid())){
//                    Toast.makeText(Chat_Activity.this, "1+", Toast.LENGTH_SHORT).show();
//                }else {
//                    Toast.makeText(Chat_Activity.this, "2+", Toast.LENGTH_SHORT).show();
//
//                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageContent = editText.getText().toString();
                editText.setText("");
                chatRecycler.scrollToPosition(messageAdapter.getItemCount() - 1);
                if(messageContent.trim().length()>0){
                    sendMessaging(messageContent);
                }
//                if (!messageContent.isEmpty()) {
//                    Message message = new Message(loadUid(), users.getId(), users.getName(),  messageContent);
//                    RealTime_Base.sendMessage("messages", loadUid(), Chat_Activity.this, message);
//                    editText.setText("");
//                }
//                DatabaseReference messagesRef = FirebaseDatabase.getInstance().getReference("messages").child(loadUid());
//                messagesRef.addListenerForSingleValueEvent(Chat_Activity.this);

            }
        });







    }

    private String loadUid() {
        SharedPreferences sharedPreferences = getSharedPreferences("saveUid", Context.MODE_PRIVATE);
        return sharedPreferences.getString("uid", "");
    }

    private String loadName() {
        SharedPreferences sharedPreferences = getSharedPreferences("saveUid", Context.MODE_PRIVATE);
        return sharedPreferences.getString("name", "");
    }

    private void sendMessaging(String message){
        String messId = UUID.randomUUID().toString();
        Message mesg = new Message(message , messId, loadName()  , users.getId() ,  loadUid() ) ;
        messageAdapter.addMessage(mesg);
        String messageId = databaseReferenceSender.push().getKey();

        databaseReferenceSender
                .child(messageId)
                .setValue(mesg);

        databaseReferenceReciver
                .child(messageId)
                .setValue(mesg);
    }




}
