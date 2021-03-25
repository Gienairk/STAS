package com.boots.repository;

import com.boots.entity.ChatRoom;
import com.boots.entity.Role;
import com.boots.entity.User;
import com.boots.entity.UserChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface UserChatRoomRepository extends JpaRepository<UserChatRoom, Long> {
    List<UserChatRoom>getAllByUser(User user);
    List<UserChatRoom> findAllByChatRoom(ChatRoom chatRoom);
    UserChatRoom getByChatRoomAndUser(ChatRoom chatRoom,User user);

}