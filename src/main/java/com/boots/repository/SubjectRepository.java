package com.boots.repository;

import com.boots.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject,Long> {

    Subject findByName(String name);
}
