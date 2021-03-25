package com.boots.entity;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class UserChatRoomId implements Serializable {

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="userId")
    private User user;
    public User getUser(){
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="chatroomId")
    private ChatRoom chatRoom;
    public ChatRoom getChatRoom(){
        return chatRoom;
    }



    public void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }
}
