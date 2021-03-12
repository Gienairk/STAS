package com.boots.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;


@Entity
@Getter
@Setter
@Table(name = "t_department" )
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String departmentName;
    @OneToMany(mappedBy = "departmentId",fetch = FetchType.EAGER)
    private Collection<Group> groups;
    @ManyToMany(mappedBy = "departments")
    private Set<User> users;

    public Department() {
    }

    public Department(String departmentName) {
        this.departmentName = departmentName;
    }
}
