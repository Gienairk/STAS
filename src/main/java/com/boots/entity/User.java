package com.boots.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Size;

import java.util.*;


@Entity
@Table(name = "t_user" )
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min=2, message = "Не меньше 2 знаков")
    private String username;
    @Size(min=2, message = "Не меньше 2 знаков")
    private String password;
    @Transient
    private String passwordConfirm;
    private String firstName;
    private String secondName;
    private String lastName;
    private String telegramName;
    private String userRight;
    @ManyToMany(fetch = FetchType.EAGER )
    private Set<Role> roles=new HashSet<>();
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Set<Group> groups=new HashSet<>();
    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<Department> departments=new HashSet<>();
    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<Subject> subjects=new HashSet<>();
    @OneToMany(mappedBy = "user")
    private Set<UserChatRoom> userChatRooms = new HashSet<UserChatRoom>();
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    public void addDepartment(Department department){
        this.departments.add(department);
    }
    public void addSubject(Subject subject){this.subjects.add(subject);}
    public void addGroup(Group group){this.groups.add(group);}
    //public void addChatRoom(ChatRoom chatRoom){this.chatRooms.add(chatRoom);}
    private Set<Role> getRoles() {
        return roles;
    }

    public void addRole(Role role){
        roles.add(role);
    }

/*
    public Set<ChatRoom> getChatRooms() {
        return chatRooms;
    }

    public void setChatRooms(Set<ChatRoom> chatRooms) {
        this.chatRooms = chatRooms;
    }

    public void leaveFromRoom(ChatRoom chatRoom){
        this.chatRooms.remove(chatRoom);
    }
*/
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTelegramName() {
        return telegramName;
    }

    public void setTelegramName(String telegramName) {
        this.telegramName = telegramName;
    }

    public String getUserRight() {
        return userRight;
    }

    public void setUserRight(String userRight) {
        this.userRight = userRight;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    public Set<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(Set<Department> departments) {
        this.departments = departments;
    }

    public Set<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<Subject> subjects) {
        this.subjects = subjects;
    }



    public String toStringForPage() {
        return lastName+" "+firstName+" "+secondName;
    }

    public Set<UserChatRoom> getUserChatRooms() {
        return userChatRooms;
    }

    public void setUserChatRooms(Set<UserChatRoom> userChatRooms) {
        this.userChatRooms = userChatRooms;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", telegramName='" + telegramName + '\'' +
                ", userRight='" + userRight + '\'' +

                '}';
    }
}
