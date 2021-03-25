package com.boots.repository;

import com.boots.entity.ChatRoom;

import com.boots.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;


public interface ChatRoomRepository extends JpaRepository<ChatRoom,Long> { 
    ChatRoom findByRoomName(String name);
    //Set<ChatRoom> getAllByUsersContains(User user);



}
