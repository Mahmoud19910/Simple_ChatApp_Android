package dev.mah.nassa.chat_app.Modles;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.auth.User;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.List;

import dev.mah.nassa.chat_app.Chat_Activity;
import dev.mah.nassa.chat_app.R;

public class ShowUsersAdapter extends RecyclerView.Adapter<ShowUsersAdapter.ViewHolder> {

    private Context context;
    private List<Users> userList;

    public ShowUsersAdapter(Context context) {
        this.context = context;
        userList = new ArrayList<>();
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.users_design, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Users users = userList.get(position);

        if(users!=null){
            holder.userName.setText(users.getName());
            holder.userEmail.setText(users.getEmail());

            // Set profile image using a library or any other method
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context , Chat_Activity.class);
                    intent.putExtra("obj" , users);
                    ((Activity)context).startActivity(intent);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView userName, userEmail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.userName);
            userEmail = itemView.findViewById(R.id.userEmail);
        }
    }

    public void addUsers(Users users){
        userList.add(users);
        notifyDataSetChanged();
    }

    public void clearAdapter(){
        userList.clear();
        notifyDataSetChanged();
    }
}

