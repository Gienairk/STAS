package com.boots.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;



@Entity

@Table(name = "t_postfix" )
public class Postfix {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long duration;
    @OneToMany(mappedBy = "postfixId",fetch = FetchType.EAGER)
    private Collection<Group> groups;

    public Postfix() {
    }

    @Override
    public String toString() {
        return "Postfix{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", duration=" + duration +
                '}';
    }

    public Postfix(String name, Long duration) {
        this.name = name;
        this.duration = duration;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Collection<Group> getGroups() {
        return groups;
    }

    public void setGroups(Collection<Group> groups) {
        this.groups = groups;
    }

    public Long getId() {
        return id;
    }
}
