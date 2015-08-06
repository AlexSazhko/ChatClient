package com.alexsazhko.chatclient.entity;


public class ChatMessage {

    private long sendTime;
    private String messageContent;
    private String userName;
    private String toUserName;
    private String messageFlag; //NEW, MESSAGE, END

    public ChatMessage(){

    }

    public ChatMessage(String messageFlag) {
        this.messageFlag = messageFlag;
    }

    public ChatMessage(long paramSendTime, String paramMsgContent, String userName, String messageFlag) {
        this.sendTime = paramSendTime;
        this.messageContent = paramMsgContent;
        this.userName = userName;
        this.messageFlag = messageFlag;
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
}
