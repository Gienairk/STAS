package com.boots.service;


import com.boots.entity.*;

import com.boots.repository.*;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;
import java.util.List;

@Service
public class ChatService {
    @Autowired
    ChatRoomRepository chatRoomRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    PostfixRepository postfixRepository;

    public List<String> getUsers(){

        List<User> userList=userRepository.findAll();
        List<String> answer=new ArrayList<>();
        for (int i = 0; i <userList.size() ; i++) {
            answer.add(userList.get(i).getUsername());
        }
        return answer;
    }
    public ChatRoom findRoomByName(String name){
        return chatRoomRepository.findByRoomName(name);
    }
    public void saveMessage(String roomName, Message message){
        Date date=new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm dd.MM.yyyy");
        String newFomart = formatter.format(date);
        Message localMessage=new Message(message);
        localMessage.setDate(newFomart);
        localMessage.setChatRoom(chatRoomRepository.findByRoomName(roomName));
        System.out.println("localMessage "+ localMessage);
        System.out.println("message "+ message);
        messageRepository.save(localMessage);
    }

    public void saveUser(String room,String user){
        System.out.println("room "+ room);
        System.out.println("user "+ user);
        ChatRoom chatRoom=chatRoomRepository.findByRoomName(room);
        User userObj=userRepository.findByUsername(user);
        System.out.println("roomObj "+chatRoom);
        System.out.println("userObj "+userObj);
        userObj.addChatRoom(chatRoom);
        userRepository.save(userObj);

    }

    public void saveRoom(ChatRoom room){
        if (chatRoomRepository.findByRoomName(room.getRoomName())==null){
            chatRoomRepository.save(room);
        }
    }

    public List<Message> getHistory(String roomId) {
        ChatRoom chatRoom=chatRoomRepository.findByRoomName(roomId);
        Set<Message> messageSet=messageRepository.getAllByChatRoom(chatRoom);
        List<Message> test=List.copyOf(messageSet);
        return test;
    }

    public List<ChatRoom> UserRoom(String userName) {
        User user=userRepository.findByUsername(userName);
        Set<ChatRoom> chatRooms=chatRoomRepository.getAllByUsersContains(user);
        List<ChatRoom> test=List.copyOf(chatRooms);
        return test;
    }
}
