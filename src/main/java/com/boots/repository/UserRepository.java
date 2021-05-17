package com.boots.repository;


import com.boots.entity.ChatRoom;
import com.boots.entity.Group;
import com.boots.entity.Subject;
import com.boots.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
    List<User> findAllByUserRightEquals(String userright);
    User findByFirstNameAndSecondNameAndLastNameAndUserRight(String firstname,String secondname,String lastname,String userright);
    List<User> findAllByGroups(Group group);

    List<User> findAllByLastName(String string);
    List<User> findAllByFirstName(String string);
    List<User> findAllBySecondName(String string);

    List<User> findAllByLastNameAndFirstName(String string,String string1);
    List<User> findAllByLastNameAndSecondName(String string,String string1);
    List<User> findAllByFirstNameAndSecondName(String string,String string1);

    List<User> findAllByFirstNameAndSecondNameAndLastName(String string,String string1,String string2);


    List<User> findAllBySubjects(Subject subject);

    User findByFirstNameAndSecondNameAndLastName(String s, String s1, String s2);

    // Page<User> findAll(Pageable pageable);
    //List<User> findAllByChatRooms(ChatRoom chatRoom);
}
