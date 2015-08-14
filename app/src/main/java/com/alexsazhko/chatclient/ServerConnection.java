package com.alexsazhko.chatclient;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.alexsazhko.chatclient.entity.ChatMessage;
import com.alexsazhko.chatclient.entity.MessageState;
import com.alexsazhko.chatclient.ui.AddContactActivity;
import com.alexsazhko.chatclient.ui.ChatRoomActivity;
import com.alexsazhko.chatclient.utils.MessageFactory;
import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

public class ServerConnection implements Runnable{

    private static volatile ServerConnection instance;

    private static Context context = ChatApplication.getInstance();

    private String jsonMessage;
    private String adress;
    private int port;
    private ReceiveMessageCallBack receiveMessageCallBack;


    private Socket socket = null;
    private DataInputStream dataInputStream = null;
    private DataOutputStream dataOutputStream = null;

    private boolean isConnected;
    private boolean isMessageRedyToSend;

    private ServerConnection(){
        loadPreference();
        isConnected = true;
        isMessageRedyToSend = false;
    }

    public static ServerConnection getInstance(){
        ServerConnection localInstance = instance;
        if (localInstance == null) {
            synchronized (ServerConnection.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ServerConnection();
                }
            }
        }
        return localInstance;
    }

    public void start(){

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
                       /* MessageState state = MessageState.valueOf(getChatMessage(receivedMessage).getMessageFlag());
                       if( state == MessageState.SEARCH & (receiveMessageCallBack instanceof AddContactActivity)){
                           receiveMessageCallBack.receiveMessage(getChatMessage(receivedMessage));
                       }
                        if (state == MessageState.MESSAGE & (receiveMessageCallBack instanceof ChatRoomActivity)) {
                            receiveMessageCallBack.receiveMessage(getChatMessage(receivedMessage));
                       }*/
                        MessageState state = MessageState.valueOf(getChatMessage(receivedMessage).getMessageFlag());
                        BaseMessage baseMessage = MessageFactory.getMessage(receivedMessage);
                        if(baseMessage != null) {
                            if (state == MessageState.SEARCH & (receiveMessageCallBack instanceof AddContactActivity)) {
                                receiveMessageCallBack.receiveMessage(MessageFactory.getMessage(receivedMessage));
                            }
                            if (state == MessageState.MESSAGE & (receiveMessageCallBack instanceof ChatRoomActivity)) {
                                receiveMessageCallBack.receiveMessage(MessageFactory.getMessage(receivedMessage));
                            }
                        }

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
    public void setMessageToSend(BaseMessage baseMessage){
        Gson gson = new Gson();
        jsonMessage = gson.toJson(baseMessage);
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
