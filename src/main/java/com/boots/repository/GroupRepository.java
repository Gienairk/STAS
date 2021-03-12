package com.boots.repository;

import com.boots.entity.Group;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface GroupRepository extends JpaRepository<Group,Long> {
    Set<Group> findAllBySubjectsNotNull();
    Group findByFullname(String fullname);
    Optional<Group> findById(Long id);



}
