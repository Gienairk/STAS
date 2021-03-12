package com.boots.repository;

import com.boots.entity.Postfix;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostfixRepository extends JpaRepository<Postfix,Long> {
    Postfix findByName(String name);
}
