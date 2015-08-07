package com.alexsazhko.chatclient.entity;


import android.os.Parcel;
import android.os.Parcelable;

public class ChatMessage implements Parcelable{

    private long sendTime;
    private String messageContent;
    private String userName;
    private String toUserName;
    private String messageFlag; //NEW, MESSAGE, END

    private boolean ownMessage = false;

    public ChatMessage(){

    }

    public ChatMessage(String messageFlag) {
        this.messageFlag = messageFlag;
    }

    public ChatMessage(long paramSendTime, String paramMsgContent, String userName, String messageFlag, boolean ownMessage) {
        this.sendTime = paramSendTime;
        this.messageContent = paramMsgContent;
        this.userName = userName;
        this.messageFlag = messageFlag;
        this.ownMessage = ownMessage;
    }

    public long getSendTime() {
        return sendTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }


    public String getMsgContent() {
        return messageContent;
    }

    public void setMsgContent(String paramMsgContent) {
        messageContent = paramMsgContent;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getMessageFlag() {
        return messageFlag;
    }

    public void setMessageFlag(String messageFlag) {
        this.messageFlag = messageFlag;
    }

    public boolean isOwnMessage() {
        return ownMessage;
    }

    public void setOwnMessage(boolean ownMessage) {
        this.ownMessage = ownMessage;
    }

    protected ChatMessage(Parcel in) {
        sendTime = in.readLong();
        messageContent = in.readString();
        userName = in.readString();
        toUserName = in.readString();
        messageFlag = in.readString();
        ownMessage = in.readByte() != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(sendTime);
        dest.writeString(messageContent);
        dest.writeString(userName);
        dest.writeString(toUserName);
        dest.writeString(messageFlag);
        dest.writeByte((byte) (ownMessage ? 0x01 : 0x00));
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ChatMessage> CREATOR = new Parcelable.Creator<ChatMessage>() {
        @Override
        public ChatMessage createFromParcel(Parcel in) {
            return new ChatMessage(in);
        }

        @Override
        public ChatMessage[] newArray(int size) {
            return new ChatMessage[size];
        }
    };

}
