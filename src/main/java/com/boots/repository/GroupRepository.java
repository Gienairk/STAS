package com.boots.repository;

import com.boots.entity.Group;

import com.boots.entity.Subject;
import com.boots.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface GroupRepository extends JpaRepository<Group,Long> {
    Set<Group> findAllBySubjectsNotNull();
    Group findByFullname(String fullname);
    Optional<Group> findById(Long id);
    List<Group>findAllByUsers(User user);
    List<Group> findAllBySubjects(Subject subject);
    List<Group>findAllByFullname(String string);
    List<Group>getAllByFullnameContains(String string);

}
