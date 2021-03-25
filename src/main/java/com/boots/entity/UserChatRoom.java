package com.boots.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "t_user_chat_room" )
public class UserChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName ="id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "chatRoom_id",referencedColumnName ="id")
    private ChatRoom chatRoom;
    private String time;

    public UserChatRoom() {
    }

    public UserChatRoom(User user, ChatRoom chatRoom) {
        this.user = user;
        this.chatRoom = chatRoom;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ChatRoom getChatRoom() {
        return chatRoom;
    }

    public void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }

    public String getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "UserChatRoom{" +
                "user=" + user +
                ", chatRoom=" + chatRoom +
                ", time='" + time + '\'' +
                '}';
    }

    public void setTime(String time) {
        this.time = time;
    }
}
