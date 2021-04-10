package com.boots.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "t_subject" )
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    @ManyToMany(mappedBy = "subjects")
    private Set<Group> groups=new HashSet<>();
    @ManyToMany(mappedBy = "subjects")
    private Set<User> users;

    public Subject() {

    }

    public String getName() {
        return name;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public Set<User> getUsers() {
        return users;
    }

    public Long getId() {
        return id;
    }

    public Subject(String name) {
        this.name = name;
    }
    public String toStringForPage(){
        return name;
    }


}
