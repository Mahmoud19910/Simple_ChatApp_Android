package dev.mah.nassa.chat_app.DataBase;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import dev.mah.nassa.chat_app.MainActivity;
import dev.mah.nassa.chat_app.Modles.Message;
import dev.mah.nassa.chat_app.Modles.Users;

public class RealTime_Base {
    public static FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();


    // ميثود ارسال رسالة
    public static void sendMessage(String collection, String sendUidUser ,  Context context, Object object) {
        firebaseDatabase.getReference().child(collection).child(sendUidUser).push().setValue(object).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(context, "Success Send", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, e.getMessage().toString() + "  Error", Toast.LENGTH_SHORT).show();
            }
        });
    }





//    public static void getAllMessages(String senderId, String retriveId, Context context, OnSuccessListener<List<Message>> listener) {
//        DatabaseReference messagesRef = FirebaseDatabase.getInstance().getReference("messages");
//
//        messagesRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@org.checkerframework.checker.nullness.qual.NonNull DataSnapshot dataSnapshot) {
//                List<Message> messageList = new ArrayList<>();
//
//                if (dataSnapshot != null) {
//                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                        Message message = snapshot.getValue(Message.class);
//
//                        if (senderId != null && retriveId != null) {
//
//                            if (message.getSenderId().equals(senderId) && message.getRecipientId().equals(retriveId) || message.getSenderId().equals(retriveId) && message.getRecipientId().equals(senderId)) {
//                                messageList.add(message);
//                            }
//
//                        }
//
//                    }
//
//                    listener.onSuccess(messageList);
//                } else {
//                    Toast.makeText(context, "dataSnap Null", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@org.checkerframework.checker.nullness.qual.NonNull DatabaseError databaseError) {
//                // Handle database error event
//            }
//        });
//    }

    public static void getAllUsersRealTimeData( Context  context , String collection ,String id, OnSuccessListener listener){
        List<Users> users = new ArrayList<>();
        DatabaseReference messagesRef = FirebaseDatabase.getInstance().getReference(collection).child(id);
        messagesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot != null) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Users users1 = snapshot.getValue(Users.class);
                        users.add(users1);
                    }

                   listener.onSuccess(users);
                } else {
                    Toast.makeText(context, "dataSnap Null", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error event
            }
        });


    }

}