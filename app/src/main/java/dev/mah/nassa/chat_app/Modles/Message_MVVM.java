package dev.mah.nassa.chat_app.Modles;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

import dev.mah.nassa.chat_app.DataBase.RealTime_Base;

public class Message_MVVM extends AndroidViewModel {

    MutableLiveData<List<Message>> listMessage = new MutableLiveData<>();
    Context context;
    public Message_MVVM(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

//    public MutableLiveData<List<Message>> getListMessage(String senderId , String retriveId ){
//        RealTime_Base.getAllMessages(senderId, retriveId, context ,  new OnSuccessListener<List<Message>>() {
//            @Override
//            public void onSuccess(List<Message> messages) {
//                listMessage.postValue(messages);
//            }
//        });
//
//        return listMessage;
//    }

}
