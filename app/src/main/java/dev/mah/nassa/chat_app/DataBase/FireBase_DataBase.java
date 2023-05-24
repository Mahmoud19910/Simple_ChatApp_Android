package dev.mah.nassa.chat_app.DataBase;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import dev.mah.nassa.chat_app.Modles.Users;

public class FireBase_DataBase {
    static FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    public static void insertOrUpdateExerciseDetails(Object object , Context context ,String collection){
        firestore.collection(collection).add(object).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                task.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    public static void insertOrUpdateData(Object object, String collection, Context context) {
        // Creating a collection reference for our Firebase Firestore database.
        CollectionReference collectionReference = firestore.collection(collection);

        // Perform a query to check if the object already exists in the collection
        Query query = collectionReference.whereEqualTo("fieldName", "fieldValue"); // Update with appropriate field and value

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();
                    if (querySnapshot != null && !querySnapshot.isEmpty()) {
                        // Object already exists, do not add it
                        Toast.makeText(context, "Object already exists", Toast.LENGTH_SHORT).show();
                    } else {
                        // Object does not exist, add it
                        collectionReference.add(object)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Toast.makeText(context, "Success - Data added", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(context, "Failed - Data addition", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                } else {
                    Toast.makeText(context, "Failed - Query execution", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    public static void getAllUsers(Context context , OnSuccessListener onSuccessListener){
        ArrayList<Users> usersArrayList =new ArrayList<>();

        firestore.collection("Users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
               List<DocumentSnapshot> list =  queryDocumentSnapshots.getDocuments();
               for(DocumentSnapshot documentSnapshot : list){
                   Map<String , Object> map = documentSnapshot.getData();

                   String id = (String) map.get("id");
                   String name = (String) map.get("name");
                   String email =(String) map.get("email");
                   String photo =(String) map.get("photo");

                   usersArrayList.add(new Users(email , id , name));


               }
               onSuccessListener.onSuccess(usersArrayList);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, e.getMessage().toString()+"", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
