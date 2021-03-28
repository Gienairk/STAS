package com.boots.controller;

import com.boots.entity.Message;
import com.boots.entity.User;
import com.boots.repository.UserRepository;
import com.boots.service.DatafinderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin
public class MessengerController {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    DatafinderService datafinderService;

    @GetMapping("/fetchAllUsers")
    public Set<String> fetchAll() {
        List<User> users=datafinderService.allUser();
        Set user=new HashSet();
        for (int i = 0; i <users.size() ; i++) {
            user.add(users.get(i).getUsername());
        }
        return user;
    }
    @GetMapping("/getCurrentUser")
    public String GetCurrentUser(@AuthenticationPrincipal User user){
        System.out.println(user.getUsername());
        return user.getUsername();
    }

    @GetMapping("/getCurrentUserRight")
    public String GetCurrentUserRight(@AuthenticationPrincipal User user){
        System.out.println(user.getUserRight());
        return user.getUserRight();
    }
    @MessageMapping("/chat/{to}")
    public void sendMessage(@DestinationVariable String to, Message message) {
        System.out.println("handling send message: " + message + " to: " + to);
            simpMessagingTemplate.convertAndSend("/topic/messages/" + to, message);

    }
}
