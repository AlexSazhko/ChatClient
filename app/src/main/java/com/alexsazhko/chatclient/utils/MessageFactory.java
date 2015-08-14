package com.alexsazhko.chatclient.utils;

import android.util.Log;

import com.alexsazhko.chatclient.BaseMessage;
import com.alexsazhko.chatclient.entity.ChatMessage;
import com.alexsazhko.chatclient.entity.ContactList;
import com.alexsazhko.chatclient.entity.MessageState;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class MessageFactory {

    public static BaseMessage getMessage(String in){
        BaseMessage baseMessage = null;
        String messageFlag = "";
        Gson gson = new Gson();


        try {
            JSONObject jsonObject = new JSONObject(in);
            messageFlag = jsonObject.getString("messageFlag");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            switch(MessageState.valueOf(messageFlag)) {
                case SEARCH:
                    baseMessage = new ContactList();
                    baseMessage = gson.fromJson(in, ContactList.class);
                    break;
                case MESSAGE:
                    baseMessage = new ChatMessage();
                    baseMessage = gson.fromJson(in, ChatMessage.class);
                    ((ChatMessage)baseMessage).setOwnMessage(false);
                    break;
            }
        }catch(IllegalArgumentException e){
            e.printStackTrace();
        }


        return baseMessage;
    }
}
