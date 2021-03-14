package com.boots.repository;


import com.boots.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    List<User> findAllByUserRightEquals(String userright);
    User findByFirstNameAndSecondNameAndLastNameAndUserRight(String firstname,String secondname,String lastname,String userright);

}
