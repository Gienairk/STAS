package com.boots.repository;

import com.boots.entity.Department;

import com.boots.entity.Postfix;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department,Long> {
    Department findByDepartmentName(String name);
}
