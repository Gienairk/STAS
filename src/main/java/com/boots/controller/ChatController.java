package com.boots.controller;

import com.boots.entity.Message;
import com.boots.entity.User;
import com.boots.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatController {
    @Autowired
    private UserService userService;

    @GetMapping("/chat")
    public String chat(Model model,@AuthenticationPrincipal User user) {
        model.addAttribute("userRight", user.getUserRight());
        //System.out.println("userRight "+user.getUserRight() );
        return "chat";
    }

    @MessageMapping("message")
    @SendTo("/chat/messages")
    public Message getMessage (Message message){
        return message;
    }

    @GetMapping("/chatRoom")
    public String chatRoom(Model model) {
        model.addAttribute("allUsers", userService.allUsers());
        return "chatRooms";
    }


}
