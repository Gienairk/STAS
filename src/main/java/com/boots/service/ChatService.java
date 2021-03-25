package com.boots.service;


import com.boots.controller.ChatAppController;
import com.boots.entity.*;

import com.boots.repository.*;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.text.SimpleDateFormat;
import java.util.*;

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
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    ChatAppController chatAppController;
    @Autowired
    UserChatRoomRepository userChatRoomRepository;

    public List<String> getUsers(){

        List<User> userList=userRepository.findAll();
        List<String> answer=new ArrayList<>();
        for (int i = 0; i <userList.size() ; i++) {
            answer.add(userList.get(i).getUsername());
        }
        return answer;
    }

    public List<String> getGroup() {
        List<Group> groupList=groupRepository.findAll();
        List<String> answer=new ArrayList<>();
        for (int i = 0; i <groupList.size() ; i++) {
            answer.add(groupList.get(i).getFullname());
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
    public void messegeDelivery(String roomName){
        ChatRoom chatRoom=chatRoomRepository.findByRoomName(roomName);
       // List<User> userList=userRepository.findAllByChatRooms(chatRoom);
        List<UserChatRoom> userChatRoomList=userChatRoomRepository.findAllByChatRoom(chatRoom);
        List<User> userList=new ArrayList<>();
        for (int i = 0; i <userChatRoomList.size() ; i++) {
            userList.add(userChatRoomList.get(i).getUser());
        }
        for (int i = 0; i <userList.size() ; i++) {
           chatAppController.sendAwardaboutMessage(userList.get(i).getUsername(),roomName);
        }

    }

    public void saveUser(String room,String user){
        ChatRoom chatRoom=chatRoomRepository.findByRoomName(room);
        User userObj=userRepository.findByUsername(user);
        System.out.println("chatRoom "+chatRoom);
        System.out.println("userObj "+userObj);
        UserChatRoom userChatRoom=new UserChatRoom();
        userChatRoom.setChatRoom(chatRoom);
        userChatRoom.setUser(userObj);
        System.out.println("userChatRoom "+userChatRoom);
        if (userChatRoomRepository.getByChatRoomAndUser(chatRoom,userObj)==null)
            userChatRoomRepository.save(userChatRoom);
       // userObj.addChatRoom(chatRoom);
        //userRepository.save(userObj);

    }
    public void leavefromRoom(String room,String user){
        User user1=userRepository.findByUsername(user);
        ChatRoom chatRoom=chatRoomRepository.findByRoomName(room);
        /*System.out.println("++++++++++++++++");
        System.out.println(user1.getChatRooms());
        System.out.println("++++++++++++++++");
        user1.leaveFromRoom(chatRoom);
        System.out.println(user1.getChatRooms());
        System.out.println("++++++++++++++++");*/
        userRepository.save(user1);
    }
    public void saveGroup(String group,String room){
        System.out.println("room "+ room);
        System.out.println("group "+ group);
        Group groups=groupRepository.findByFullname(group);
        List<User> userSet=userRepository.findAllByGroups(groups);
        String name="";
        for (int i = 0; i <userSet.size() ; i++) {
            name=userSet.get(i).getUsername();
            chatAppController.updateRoom(name,room);
            saveUser(room,name);
        }

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
       // Set<ChatRoom> chatRooms=chatRoomRepository.getAllByUsersContains(user);
        Set<UserChatRoom>chatRooms=userChatRoomRepository.getAllByUser(user);
        List<UserChatRoom> test=List.copyOf(chatRooms);
        List <ChatRoom> answer=new ArrayList<>();
        for (int i = 0; i <test.size() ; i++) {
            answer.add(test.get(i).getChatRoom());
        }
        System.out.println(answer);
        return answer;
    }


}
