package com.boots.controller;


import com.boots.entity.ChatRoom;
import com.boots.entity.Message;
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


import java.util.ArrayList;
import java.util.List;

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


    @MessageMapping("/test")
    public void test(){
        System.out.println("============= я в тесте! ==================");

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

   /* @MessageMapping("/chat/{roomId}/addUser")
    public void addUser(@DestinationVariable String roomId, @Payload Message chatMessage,SimpMessageHeaderAccessor headerAccessor) {
        String currentRoomId = (String) headerAccessor.getSessionAttributes().put("room_id", roomId);
        System.out.println(chatMessage.toString());
        if (currentRoomId != null) {
            Message leaveMessage = new Message();
            leaveMessage.setType(Message.MessageType.LEAVE);
            leaveMessage.setFromLogin(chatMessage.getFromLogin());
            addmessage(currentRoomId,chatMessage);
            messagingTemplate.convertAndSend(format("/topic/%s", currentRoomId), leaveMessage);
        }
        headerAccessor.getSessionAttributes().put("username", chatMessage.getFromLogin());
        addmessage(roomId,chatMessage);
        messagingTemplate.convertAndSend(format("/topic/%s", roomId), chatMessage);
    }*/


   /* @SubscribeMapping("/chat/rooms")
    public List<ChatRoom> listOfRoom()
    {

        //System.out.println("Rooms: "+rooms.size());
        return rooms;
    }*/


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
    @SubscribeMapping("/chat/getGroupList")
    public  List<String>getGroupList()
    {
        return chatService.getGroup();
    }

    /*@MessageMapping("/chat/rooms")
    public void addRoom(@Payload ChatRoom chatRoom)
    {
        System.out.println("+++++++++++++++++++++++++++++++++++"+chatRoom);
        chatService.saveRoom(chatRoom);
        messagingTemplate.convertAndSend("/topic/list", rooms);
    }*/

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
        System.out.println("===============");
        System.out.println("userName= "+userName);
        System.out.println("chatRoomname= "+chatRoomName);

        System.out.println("===============");
        messagingTemplateTest.convertAndSend("/topic/chat/"+userName , chatRoomName);
    }

    @MessageMapping("/chat/rooms/{groupName}/addGroup")
    public void addGroupToRoom(@DestinationVariable String groupName,@Payload ChatRoom chatRoom){
        System.out.println("+++++++++++++++");
        System.out.println(groupName);
        System.out.println(chatRoom.getRoomName());
        System.out.println("+++++++++++++++");
        chatService.saveGroup(groupName,chatRoom.getRoomName());


    }

    /*@MessageMapping("/chat/{roomId}/leaveuser")
    public void leaveRoom(@DestinationVariable String roomId, @Payload Message chatMessage,SimpMessageHeaderAccessor headerAccessor)
    {
        String currentRoomId = (String) headerAccessor.getSessionAttributes().put("room_id", roomId);
        if (currentRoomId != null) {
            Message leaveMessage = new Message();
            leaveMessage.setType(Message.MessageType.LEAVE);
            leaveMessage.setFromLogin(chatMessage.getFromLogin());
            //addmessage(currentRoomId,chatMessage);
            messagingTemplate.convertAndSend(format("/topic/%s", currentRoomId), leaveMessage);
        }
    }*/

/*
    private void addmessage(String roomid, Message message) {
        for(ChatRoom room: rooms)
        {
            if(room.getRoomName().equals(roomid))
            {
                List<Message> messages = room.getMessages();
                messages.add(message);
                room.setMessages(messages);
                break;
            }
        }
    }
*/
    @SubscribeMapping("chat/{roomId}/getPrevious")
    public List<Message> getPreviousMessages(@DestinationVariable String roomId)
    {
        List<Message> test =chatService.getHistory(roomId);//отрисовка при входе в чат
        for (int i = 0; i <test.size() ; i++) {
            test.get(i).setChatRoom(null);
        }
        return test;
    }

    @SubscribeMapping("chat/{userName}/getChats")
    public List<String> getUserRoom(@DestinationVariable String userName)
    {
        List<ChatRoom> test= chatService.UserRoom(userName);
        List<String> name=new ArrayList<>();
        for (int i = 0; i < test.size(); i++) {
            name.add(test.get(i).getRoomName());
        }
        return name;
    }

}