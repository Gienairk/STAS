package com.boots.controller;


import com.boots.entity.ChatRoom;
import com.boots.entity.ChatRoomFormat;
import com.boots.entity.Message;
import com.boots.entity.User;
import com.boots.service.ChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;


import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.String.format;
@Controller
public class ChatAppController {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);
    @Autowired
    ChatService chatService;
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;
    @Autowired
    private SimpMessageSendingOperations messagingTemplateTest;


    List<ChatRoom> rooms;


    @MessageMapping("/changeChat/{userName}")
    public void test(@DestinationVariable String userName, @Payload String chatName){
        Date date=new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy");
        String newFomart = formatter.format(date);
        //System.out.println(userName+" "+ chatName+" "+newFomart);
        chatService.saveTimeData(newFomart,userName,chatName);
    }

    @Autowired
    public void ChatController()
    {
        rooms = new ArrayList<ChatRoom>();
    }

    @MessageMapping("/chat/{roomId}/sendMessage")
    public void sendMessage(@DestinationVariable String roomId, @Payload Message chatMessage) {
        System.out.println("saveMessage start");
        chatService.saveMessage(roomId,chatMessage);
        System.out.println("saveMessage end");
        //addmessage(roomId,chatMessage);
        messagingTemplate.convertAndSend(format("/topic/%s", roomId), chatMessage);
        chatService.messegeDelivery(roomId);
       // messagingTemplate.convertAndSend(format("/topic/newMessage/%s", "admin"), chatMessage);
    }
    public void sendAwardaboutMessage(String username, String roomName){
        messagingTemplate.convertAndSend(format("/topic/newMessage/%s", username), roomName);
    }







    @SubscribeMapping("/chat/{chatRoom}/CreateRoom")
    public Boolean createRoom(@DestinationVariable String chatRoom)
    {
        if (chatService.findRoomByName(chatRoom)==null){
            chatService.saveRoom(new ChatRoom(chatRoom));
            //messagingTemplate.convertAndSend("/topic/list", rooms);
            return true;
        }
        else
            return false;
    }
    @SubscribeMapping("/chat/getUserList")
    public  List<String>getAllUser()
    {
       return chatService.getUsers();
    }

    @SubscribeMapping("/chat/getTeatherList")
    public  List<String>getAllTeathers()
    {
        return chatService.getTeathers();
    }

    @SubscribeMapping("/chat/getGroupList")
    public  List<String>getGroupList()
    {
        return chatService.getGroup();
    }


    @MessageMapping("/chat/rooms/{userName}")
    public void addUserToRoom(@DestinationVariable String userName,@Payload ChatRoom chatRoom){

        chatService.saveUser(chatRoom.getRoomName(),userName);
    }



    @MessageMapping("/chat/roomsLeave/{userName}")
    public void deleteUserFromRoom(@DestinationVariable String userName,@Payload ChatRoom chatRoom){
        chatService.leavefromRoom(chatRoom.getRoomName(),userName);
    }

    @MessageMapping("chat/updateRoom/{userName}")
    public void updateRoom(@DestinationVariable String userName,@Payload String chatRoomName){
       /* System.out.println("===============");
        System.out.println("userName= "+userName);
        System.out.println("chatRoomname= "+chatRoomName);
        System.out.println("===============");*/
        messagingTemplateTest.convertAndSend("/topic/chat/"+userName , chatRoomName);
    }

    @MessageMapping("/chat/rooms/{groupName}/addGroup")
    public void addGroupToRoom(@DestinationVariable String groupName,@Payload ChatRoom chatRoom){
      /*  System.out.println("+++++++++++++++");
        System.out.println(groupName);
        System.out.println(chatRoom.getRoomName());
        System.out.println("+++++++++++++++");*/
        chatService.saveGroup(groupName,chatRoom.getRoomName());


    }




    @SubscribeMapping("chat/{roomId}/getPrevious")
    public List<Message> getPreviousMessages(@DestinationVariable String roomId)
    {
        List<Message> test =chatService.getHistory(roomId);//?????????????????? ?????? ?????????? ?? ??????
        for (int i = 0; i <test.size() ; i++) {
            test.get(i).setChatRoom(null);
        }
        return test;
    }

    @SubscribeMapping("chat/{userName}/getChats")
    public  List<ChatRoomFormat> getUserRoom(@DestinationVariable String userName)
    {
        List<ChatRoomFormat> chatRoomFormatData=chatService.UserRoomTest(userName);
            //System.out.println(chatRoomFormatData);
        Collections.sort(chatRoomFormatData);
        return chatRoomFormatData;
    }
    @SubscribeMapping("chat/{roomId}/getUsers")
    public  List<String> getUserInRoom(@DestinationVariable String roomId)
    {
        List<String> ans=chatService.getUsersInChat(roomId);
       return ans;
    }

}