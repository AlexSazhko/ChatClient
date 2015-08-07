package com.alexsazhko.chatclient.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Contact implements Parcelable {
    private int id;
    private String name = null;
    private boolean isConnected = false;
    private String pictureName;
    private List<ChatMessage> messagesList;

    public Contact(int id, String name){
        this.name = name;
        this.id = id;
        messagesList = new ArrayList<ChatMessage>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setIsConnected(boolean isConnected) {
        this.isConnected = isConnected;
    }

    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }

    public List<ChatMessage> getMessagesList() {
        return messagesList;
    }

    public void setMessagesList(List<ChatMessage> messagesList) {
        this.messagesList = messagesList;
    }

    protected Contact(Parcel in) {
        id = in.readInt();
        name = in.readString();
        isConnected = in.readByte() != 0x00;
        pictureName = in.readString();
        if (in.readByte() == 0x01) {
            messagesList = new ArrayList<ChatMessage>();
            in.readList(messagesList, ChatMessage.class.getClassLoader());
        } else {
            messagesList = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeByte((byte) (isConnected ? 0x01 : 0x00));
        dest.writeString(pictureName);
        if (messagesList == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(messagesList);
        }
    }

    @SuppressWarnings("unused")
    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };
}
