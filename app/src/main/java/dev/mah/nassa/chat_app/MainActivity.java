package dev.mah.nassa.chat_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import dev.mah.nassa.chat_app.DataBase.FireBase_DataBase;
import dev.mah.nassa.chat_app.DataBase.RealTime_Base;
import dev.mah.nassa.chat_app.Firebase_Google.Gmai_Auth;
import dev.mah.nassa.chat_app.Modles.Message;
import dev.mah.nassa.chat_app.Modles.Users;

public class MainActivity extends AppCompatActivity implements GmailDataListener {

    private SignInButton google_But;
    public boolean isFound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        google_But = findViewById(R.id.sign_in_button);

        Gmai_Auth.createGoogleAuth(MainActivity.this);

        google_But.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gmai_Auth.onClickGoogleBut(MainActivity.this);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Gmai_Auth.onResultGoolgleAuth(MainActivity.this, requestCode, data);
    }


    @Override
    public void getGmailData(String id, String email, String name, String urlPhoto) {

        if (id != null || email != null) {
            Users users = new Users(email, id, name);


//            DatabaseReference messagesRef = FirebaseDatabase.getInstance().getReference("Users").child(id);
//            messagesRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    boolean isFound = false;
//
//                    if (dataSnapshot != null) {
//                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                            Users users1 = snapshot.getValue(Users.class);
//
//                            // Compare the values of the retrieved Users object with the target object
//                            if (users1 != null && users1.getId().equals(id) && users1.getName().equals(name) && users1.getEmail().equals(email) ) {
//                                isFound = true;
//                                break;
//                            }
//                        }
//
//                        if (!isFound) {
//
//                        }
//                    } else {
//                        Toast.makeText(MainActivity.this, "dataSnap Null", Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//                    // Handle database error event
//                }
//            });
           DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
           databaseReference.child(id).setValue(users).addOnSuccessListener(new OnSuccessListener<Void>() {
               @Override
               public void onSuccess(Void unused) {
                   Toast.makeText(MainActivity.this, "add", Toast.LENGTH_SHORT).show();
               }
           }).addOnFailureListener(new OnFailureListener() {
               @Override
               public void onFailure(@NonNull Exception e) {
                   Toast.makeText(MainActivity.this, e.getMessage().toString()+"", Toast.LENGTH_SHORT).show();
               }
           });


            Intent intent = new Intent(MainActivity.this, ShowUsers_Activity.class);
            intent.putExtra("obj", users);
            startActivity(intent);
        }
    }
}