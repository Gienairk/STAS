package com.boots.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChatRoomFormat implements Comparable<ChatRoomFormat> {
    String chatname;
    Boolean haveNewMessage;
    String date;

    public ChatRoomFormat() {
    }

    public ChatRoomFormat(String chatname, Boolean haveNewMessage, String date) {
        this.chatname = chatname;
        this.haveNewMessage = haveNewMessage;
        this.date = date;
    }

    public String getChatname() {
        return chatname;
    }

    public void setChatname(String chatname) {
        this.chatname = chatname;
    }

    public Boolean getHaveNewMessage() {
        return haveNewMessage;
    }

    public void setHaveNewMessage(Boolean haveNewMessage) {
        this.haveNewMessage = haveNewMessage;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ChatRoomFormat{" +
                "chatname='" + chatname + '\'' +
                ", haveNewMessage=" + haveNewMessage +
                ", date='" + date + '\'' +
                '}';
    }
    @Override
    public int compareTo(ChatRoomFormat o) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd.MM.yyyy");
        LocalDateTime first= LocalDateTime.parse(getDate(),formatter);
        LocalDateTime second= LocalDateTime.parse(o.getDate(),formatter);
        return second.compareTo(first);
    }
}
