package com.boots.service;


import com.boots.controller.ChatAppController;
import com.boots.entity.*;

import com.boots.repository.*;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    public List<String> getTeathers(){

        List<User> userList=userRepository.findAllByUserRightEquals("Teather");//Teather
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
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy");
        String newFomart = formatter.format(date);
        Message localMessage=new Message(message);
        localMessage.setDate(newFomart);
        localMessage.setChatRoom(chatRoomRepository.findByRoomName(roomName));
        System.out.println("localMessage "+ localMessage);
        System.out.println("message "+ message);
        messageRepository.save(localMessage);
    }
    public void saveTimeData(String time,String userName,String chatName){
        User user=userRepository.findByUsername(userName);
        ChatRoom chatRoom=chatRoomRepository.findByRoomName(chatName);
        UserChatRoom userChatRoom=userChatRoomRepository.getByChatRoomAndUser(chatRoom,user);
        userChatRoom.setTime(time);
        userChatRoomRepository.save(userChatRoom);
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
        UserChatRoom userChatRoom=userChatRoomRepository.getByChatRoomAndUser(chatRoom,user1);
        /*System.out.println("++++++++++++++++");
        System.out.println(user1.getChatRooms());
        System.out.println("++++++++++++++++");
        user1.leaveFromRoom(chatRoom);
        System.out.println(user1.getChatRooms());
        System.out.println("++++++++++++++++");*/
        userChatRoomRepository.delete(userChatRoom);
        checkRoomIsEmptyAndDel(chatRoom);
    }

    public void checkRoomIsEmptyAndDel(ChatRoom chatRoom){
        ChatRoom chatr=new ChatRoom();
        if (userChatRoomRepository.getAllByChatRoom(chatRoom).size()==0){
            List<Message> mes=messageRepository.getAllByChatRoom(chatRoom);
            messageRepository.deleteAll(mes);
            chatr=chatRoomRepository.findByRoomName(chatRoom.getRoomName());
            chatRoomRepository.delete(chatr);
        }
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
        List<Message> messageSet=messageRepository.getAllByChatRoom(chatRoom);
        return messageSet;
    }

    public Map<String,Boolean> UserRoom(String userName) {
        User user=userRepository.findByUsername(userName);
       // Set<ChatRoom> chatRooms=chatRoomRepository.getAllByUsersContains(user);
        List<UserChatRoom> userChatRoomList=userChatRoomRepository.getAllByUser(user);;
        Map<String,Boolean> chatMessageMap=new HashMap<String,Boolean>();
        List<Message> messages=new ArrayList<>();
        UserChatRoom userChatRoomprom;
        Message ms;
        LocalDateTime first;
        LocalDateTime second;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd.MM.yyyy");
        for (int i = 0; i <userChatRoomList.size() ; i++) {
            messages=messageRepository.getAllByChatRoom(userChatRoomList.get(i).getChatRoom());
            userChatRoomprom=userChatRoomList.get(i);
                if (messages.size()>0 && userChatRoomprom.getTime()!=null){
                    ms=messages.get(messages.size()-1);
                    first=LocalDateTime.parse(ms.getDate(),formatter);
                    second=LocalDateTime.parse(userChatRoomprom.getTime(),formatter);;
                    chatMessageMap.put(userChatRoomprom.getChatRoom().getRoomName(),first.isAfter(second));
            }
                else {
                    chatMessageMap.put(userChatRoomprom.getChatRoom().getRoomName(),false);
            }
        }

        /*
        List <ChatRoom> answer=new ArrayList<>();
        for (int i = 0; i <userChatRoomList.size() ; i++) {
            answer.add(userChatRoomList.get(i).getChatRoom());
        }
        System.out.println(answer);*/

        return chatMessageMap;
    }


    public List<ChatRoomFormat> UserRoomTest(String userName) {
        User user=userRepository.findByUsername(userName);
        // Set<ChatRoom> chatRooms=chatRoomRepository.getAllByUsersContains(user);
        List<UserChatRoom> userChatRoomList=userChatRoomRepository.getAllByUser(user);;
        Map<String,Boolean> chatMessageMap=new HashMap<String,Boolean>();
        List<ChatRoomFormat> chatRoomFormat=new ArrayList<>();
        List<Message> messages=new ArrayList<>();
        UserChatRoom userChatRoomprom;
        Message ms;
        LocalDateTime first;
        LocalDateTime second;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd.MM.yyyy");
        for (int i = 0; i <userChatRoomList.size() ; i++) {
            messages=messageRepository.getAllByChatRoom(userChatRoomList.get(i).getChatRoom());
            userChatRoomprom=userChatRoomList.get(i);
            if (messages.size()>0 && userChatRoomprom.getTime()!=null){
                ms=messages.get(messages.size()-1);
                first=LocalDateTime.parse(ms.getDate(),formatter);
                second=LocalDateTime.parse(userChatRoomprom.getTime(),formatter);;
                //chatMessageMap.put(userChatRoomprom.getChatRoom().getRoomName(),first.isAfter(second));
                chatRoomFormat.add(new ChatRoomFormat(userChatRoomprom.getChatRoom().getRoomName(),first.isAfter(second),ms.getDate()));
            }
            else {
                chatRoomFormat.add(new ChatRoomFormat(userChatRoomprom.getChatRoom().getRoomName(),false,"0"));
                //chatMessageMap.put(userChatRoomprom.getChatRoom().getRoomName(),false);
            }
        }

        /*
        List <ChatRoom> answer=new ArrayList<>();
        for (int i = 0; i <userChatRoomList.size() ; i++) {
            answer.add(userChatRoomList.get(i).getChatRoom());
        }
        System.out.println(answer);*/

        return chatRoomFormat;
    }


}
