package com.alexsazhko.chatclient.entity;


import com.alexsazhko.chatclient.BaseMessage;

import java.util.ArrayList;

public class ContactList implements BaseMessage{

    private String messageFlag;
    private ArrayList<Contact> clients;

    public ArrayList<Contact> getClients() {
        return clients;
    }

    public void setClients(ArrayList<Contact> clients) {
        this.clients = clients;
    }


    public ContactList(){
        messageFlag = MessageState.SEARCH.name();
        clients = new ArrayList<Contact>();
    }


}
