package dev.mah.nassa.chat_app.Modles;

public class Message {
    private String msgId;
    private String senderId;
    private String recipientId;
    private String name;
    private String message;

    public Message( String message , String msgId, String name , String recipientId, String senderId) {
        this.setMsgId(msgId);
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.name = name;
        this.message = message;
    }

    public Message() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }
}
