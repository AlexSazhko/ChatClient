package com.alexsazhko.chatclient;


import com.alexsazhko.chatclient.entity.ChatMessage;

public interface ReceiveMessageCallBack {

    void receiveMessage(ChatMessage message);
}
