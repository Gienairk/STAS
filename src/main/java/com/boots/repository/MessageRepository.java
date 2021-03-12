package com.boots.repository;

import com.boots.entity.ChatRoom;
import com.boots.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface MessageRepository extends JpaRepository<Message,Long> {
    Set<Message> getAllByChatRoom(ChatRoom id);

}
