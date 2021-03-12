package com.boots.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "t_message" )
public class Message implements Serializable {
    public enum MessageType {
        CHAT, JOIN, LEAVE
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    private String date;
    private String fromLogin;
    private MessageType messageType;
    @ManyToOne(optional = false)
    @JoinColumn(name = "chatRoomId",referencedColumnName ="id")
    private ChatRoom chatRoom;

    public Message() {
    }

    public Message(Message message){
        this.message=message.getMessage();
        this.date=message.getDate();
        this.fromLogin=message.fromLogin;
        this.messageType=message.getMessageType();
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }



    public MessageType getMessageType() {
        return messageType;
    }

    public ChatRoom getChatRoom() {
        return chatRoom;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String content) {
        this.message = content;
    }

    public MessageType getType() {
        return messageType;
    }

    public void setType(MessageType messageType) {
        this.messageType = messageType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



    public String getFromLogin() {
        return fromLogin;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", date=" + date +
                ", fromLogin='" + fromLogin + '\'' +
                ", messageType=" + messageType +
                ", chatRoom=" + chatRoom +
                '}';
    }

    public void setFromLogin(String fromLogin) {
        this.fromLogin = fromLogin;
    }




}
