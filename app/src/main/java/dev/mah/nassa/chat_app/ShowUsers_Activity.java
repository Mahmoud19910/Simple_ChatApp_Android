package dev.mah.nassa.chat_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import dev.mah.nassa.chat_app.DataBase.FireBase_DataBase;
import dev.mah.nassa.chat_app.DataBase.RealTime_Base;
import dev.mah.nassa.chat_app.Firebase_Google.Gmai_Auth;
import dev.mah.nassa.chat_app.Modles.ShowUsersAdapter;
import dev.mah.nassa.chat_app.Modles.Users;

public class ShowUsers_Activity extends AppCompatActivity {

    ImageView signOut;
    RecyclerView showRecucler;
    Users myInfo;
    ShowUsersAdapter showUsersAdapter;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_users);


        Intent intent = getIntent();
        myInfo = (Users) intent.getSerializableExtra("obj");

        signOut= findViewById(R.id.signOut);
        showRecucler= findViewById(R.id.showUsersRecycler);

        //Adapter
        showUsersAdapter = new ShowUsersAdapter(ShowUsers_Activity.this);
        showRecucler.setAdapter(showUsersAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ShowUsers_Activity.this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        showRecucler.setLayoutManager(layoutManager);

        saveUid(myInfo.getId() , myInfo.getName());



        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showUsersAdapter.clearAdapter();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String uid = snapshot.getKey();
                    Users users = snapshot.getValue(Users.class);
                    Toast.makeText(ShowUsers_Activity.this, users.getId()+"", Toast.LENGTH_SHORT).show();
                    showUsersAdapter.addUsers(users);
                    if (users != null) {

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error event
            }
        });


        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gmai_Auth.onSignOut(ShowUsers_Activity.this);
                startActivity(new Intent(ShowUsers_Activity.this , MainActivity.class));
            }
        });






    }


    // حفظ id
    private void saveUid(String uid, String name) {
        if (uid != null) {
            SharedPreferences sharedPreferences = getSharedPreferences("saveUid", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("uid", uid);
            editor.putString("name" , name);
            Toast.makeText(this, "save", Toast.LENGTH_SHORT).show();
            editor.apply();
        }

    }

    // جلب رقم المعرف للمستخد
    private String loadUid() {
        SharedPreferences sharedPreferences = getSharedPreferences("saveUid", Context.MODE_PRIVATE);
        return sharedPreferences.getString("uid", "");
    }

    // جلب رقم المعرف للمستخد
    private String loadName() {
        SharedPreferences sharedPreferences = getSharedPreferences("saveUid", Context.MODE_PRIVATE);
        return sharedPreferences.getString("name", "");
    }
}