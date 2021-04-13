package com.boots.repository;

import com.boots.entity.Postfix;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostfixRepository extends JpaRepository<Postfix,Long> {
    Postfix findByName(String name);
    List<Postfix> getAllByName(String name);
}
