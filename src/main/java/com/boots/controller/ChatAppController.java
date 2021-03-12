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


    List<ChatRoom> rooms;

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
        addmessage(roomId,chatMessage);
        messagingTemplate.convertAndSend(format("/topic/%s", roomId), chatMessage);
    }

    @MessageMapping("/chat/{roomId}/addUser")
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
    }


    @SubscribeMapping("/chat/rooms")
    public List<ChatRoom> listOfRoom()
    {

        //System.out.println("Rooms: "+rooms.size());
        return rooms;
    }

    @MessageMapping("/chat/rooms")
    public void addRoom(@Payload ChatRoom chatRoom)
    {
        chatService.saveRoom(chatRoom);
        messagingTemplate.convertAndSend("/topic/list", rooms);
    }

    @MessageMapping("/chat/rooms/{userName}")
    public void addUserToRoom(@DestinationVariable String userName,@Payload ChatRoom chatRoom){
        chatService.saveUser(chatRoom.getRoomName(),userName);

    }
    @MessageMapping("/chat/{roomId}/leaveuser")
    public void leaveRoom(@DestinationVariable String roomId, @Payload Message chatMessage,SimpMessageHeaderAccessor headerAccessor)
    {
        String currentRoomId = (String) headerAccessor.getSessionAttributes().put("room_id", roomId);
        System.out.println("---------------------------");
        System.out.println(roomId);
        System.out.println(chatMessage.toString());
        System.out.println("---------------------------");
        if (currentRoomId != null) {
            Message leaveMessage = new Message();
            leaveMessage.setType(Message.MessageType.LEAVE);
            leaveMessage.setFromLogin(chatMessage.getFromLogin());
            addmessage(currentRoomId,chatMessage);
            messagingTemplate.convertAndSend(format("/topic/%s", currentRoomId), leaveMessage);
        }
    }


    private void addmessage(String roomid, Message message)
    {
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