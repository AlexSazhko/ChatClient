package com.alexsazhko.chatclient.entity;

import com.alexsazhko.chatclient.BaseMessage;

public class ContactNew implements BaseMessage{

    private String messageFlag;
    private Contact contact;


    public ContactNew(){
        messageFlag = MessageState.NEW.name();
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }


}
