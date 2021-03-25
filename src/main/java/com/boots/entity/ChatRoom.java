package com.boots.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "t_chatRoom" )
public class ChatRoom implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToMany(mappedBy = "chatRoom",fetch = FetchType.EAGER)
    private List<Message> messages=new ArrayList<>();
   /* @ManyToMany(mappedBy = "chatRooms")
    private List<User>users=new ArrayList<>();*/
    String roomName;
    @OneToMany(mappedBy = "chatRoom")
    private Set<UserChatRoom> userChatRooms = new HashSet<UserChatRoom>();

    public ChatRoom() {
    }





    public Long getId() {
        return id;
    }

    public ChatRoom(String roomName) {
        this.roomName = roomName;
    }
    public void addMessage(Message message){
        messages.add(message);
    }
    public String getRoomName() {
        return roomName;
    }


    public void setRoomName(String roomid) {
        this.roomName = roomid;
    }

    public List<Message> getMessages() {
        return messages;
    }
/*
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
    public void addUser(User user){
        users.add(user);
    }*/

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "ChatRoom{" +
                "id=" + id +
                ", roomid='" + roomName + '\'' +


                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChatRoom)) return false;
        ChatRoom chatRoom = (ChatRoom) o;
        return getId().equals(chatRoom.getId()) &&
                getRoomName().equals(chatRoom.getRoomName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getRoomName());
    }
}
