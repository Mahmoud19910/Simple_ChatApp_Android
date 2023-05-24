package dev.mah.nassa.chat_app.Modles;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.List;

import dev.mah.nassa.chat_app.Chat_Activity;
import dev.mah.nassa.chat_app.R;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolderMessage> {

    private Context context;
    private List<Message> messagesList;
    private String loadId;

    public MessageAdapter(Context context , String loadId) {
        this.context = context;
        this.loadId = loadId;
        messagesList = new ArrayList<>();
    }

    public void addMessage(Message message){
        messagesList.add(0 , message);
        notifyItemInserted(0);
    }

    public void clearAdapter(){
        messagesList.clear();
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolderMessage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.message_design, parent, false);
        return new ViewHolderMessage(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderMessage holder, int position) {
     Message message = messagesList.get(position);

        holder.userName.setText(message.getName());
        holder.textMessage.setText(message.getMessage());

        if (messagesList.get(position).getSenderId().equals(loadId)) {
            holder.parentLayout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    holder.parentLayout.getLayoutParams().width,
                    holder.parentLayout.getLayoutParams().height
            );
            layoutParams.gravity = Gravity.END;
            layoutParams.setMargins(60 , 10 , 10 , 10);// Set other margins if needed
            holder.parentLayout.setLayoutParams(layoutParams);
            holder.parentLayout.setBackgroundColor(context.getColor(R.color.purple_200));
        } else {
            holder.parentLayout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    holder.parentLayout.getLayoutParams().width,
                    holder.parentLayout.getLayoutParams().height
            );
            layoutParams.gravity = Gravity.START;
            layoutParams.setMargins(10 , 10 , 60 , 10);// Set other margins if needed
            holder.parentLayout.setLayoutParams(layoutParams);
            holder.parentLayout.setBackgroundColor(context.getColor(R.color.teal_200));
        }


    }

    @Override
    public int getItemCount() {
        return messagesList.size();
    }

    public class ViewHolderMessage extends RecyclerView.ViewHolder {
        CircularImageView profileImage;
        TextView userName, textMessage;
        LinearLayout parentLayout;

        public ViewHolderMessage(@NonNull View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.profile_iv_image);
            userName = itemView.findViewById(R.id.userName);
            textMessage = itemView.findViewById(R.id.message);
            parentLayout = itemView.findViewById(R.id.parentLayout);
        }
    }

    public void scrollToBottom(RecyclerView recyclerView) {
        if (getItemCount() > 0) {
            recyclerView.scrollToPosition(getItemCount() - 1);
        }
    }


}

