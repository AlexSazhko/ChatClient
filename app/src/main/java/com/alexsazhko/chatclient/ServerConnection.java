package com.alexsazhko.chatclient;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.alexsazhko.chatclient.entity.ChatMessage;
import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ServerConnection implements Runnable{

    private String jsonMessage;
    private String adress;
    private int port;
    private Context context;
    private ReceiveMessageCallBack receiveMessageCallBack;


    private Socket socket = null;
    private DataInputStream dataInputStream = null;
    private DataOutputStream dataOutputStream = null;

    private boolean isConnected;
    private boolean isMessageRedyToSend;

    ServerConnection(Context context){
        this.context = context;
        loadPreference();
        isConnected = true;
        isMessageRedyToSend = false;
    }

    private void loadPreference(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        adress = preferences.getString("adress", "192.168.1.37");
        port = Integer.valueOf(preferences.getString("port", "6060"));
    }

    @Override
    public void run() {

        try {
            socket = new Socket(adress, port); // Creating the server socket.
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataInputStream = new DataInputStream(socket.getInputStream());

            if (socket != null) {
                while (isConnected) {
                    if(dataInputStream.available() > 0) {
                        String receivedMessage = dataInputStream.readUTF();
                        receiveMessageCallBack.receiveMessage(getChatMessage(receivedMessage));
                    }
                    if(isMessageRedyToSend){
                        dataOutputStream.writeUTF(jsonMessage);
                        dataOutputStream.flush();

                        isMessageRedyToSend = false;
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            } else {
                System.out.println("Server has not bean started on port" + String.valueOf(port));
            }
        } catch (UnknownHostException e) {
            System.out.println("Faild to connect server " + adress);
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Faild to connect server " + adress);
            e.printStackTrace();
        } finally {
            //Close the stream
            if (dataOutputStream != null) {
                try {
                    dataOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //Close the stream
            if (dataInputStream != null) {
                try {
                    dataInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //Close the socket
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } //End of finally

    }

    //It will be called from ChatRoomActivity
    public void setMessageToSend(ChatMessage msg){
        Gson gson = new Gson();
        jsonMessage = gson.toJson(msg);
        isMessageRedyToSend = true;
    }

    private ChatMessage getChatMessage(String json){
        Gson gson = new Gson();
        ChatMessage chatMessage = gson.fromJson(json, ChatMessage.class);
        chatMessage.setOwnMessage(false);
        return chatMessage;
    }

    public void setCallBack(ReceiveMessageCallBack receiveMessageCallBack) {
        this.receiveMessageCallBack = receiveMessageCallBack;
    }

}
