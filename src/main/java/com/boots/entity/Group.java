package com.boots.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "t_group" )
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullname;
    private String number;
    private Long courseNumber;
    @ManyToMany(mappedBy = "groups")
    private Set<User> users;

    @ManyToOne(optional = false,cascade = CascadeType.ALL)
    @JoinColumn(name = "postfix_id",referencedColumnName ="id")
    private Postfix postfixId;

    @ManyToOne(optional = false,cascade = CascadeType.ALL)
    @JoinColumn(name = "department_id" ,referencedColumnName ="id")
    private Department departmentId;

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<Subject> subjects=new HashSet<>();


    public void addSubject(Subject subject){
        subjects.add(subject);
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", full name='" + fullname + '\'' +

                '}';
    }
    public String toStringForPage() {
        return fullname;
    }

    public Group() {

    }


    public Group(String groupNumber, Long courseNumber, Postfix postfixId, Department departmentId,String fullname) {
        this.number = groupNumber;
        this.courseNumber = courseNumber;
        this.postfixId = postfixId;
        this.departmentId = departmentId;
        this.fullname=fullname;

    }
    public void upCourse(){
      /*  String promName=this.fullname;
        Long prName=Long.parseLong(promName);
        this.fullname=promName+100;*/
        Long prName=Long.parseLong(this.number);
        this.number=String.valueOf(prName+100);
        String[] postfix=fullname.split("-");
        this.fullname=number+"-"+postfix[1];
        this.courseNumber++;

    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setCourseNumber(Long courseNumber) {
        this.courseNumber = courseNumber;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public void setPostfixId(Postfix postfixId) {
        this.postfixId = postfixId;
    }

    public void setDepartmentId(Department departmentId) {
        this.departmentId = departmentId;
    }

    public void setSubjects(Set<Subject> subjects) {
        this.subjects = subjects;
    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public Long getCourseNumber() {
        return courseNumber;
    }

    public Set<User> getUsers() {
        return users;
    }

    public Postfix getPostfixId() {
        return postfixId;
    }

    public Department getDepartmentId() {
        return departmentId;
    }

    public Set<Subject> getSubjects() {
        return subjects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Group)) return false;
        Group group = (Group) o;
        return Objects.equals(getId(), group.getId()) &&
                Objects.equals(getNumber(), group.getNumber()) &&
                Objects.equals(getCourseNumber(), group.getCourseNumber()) &&
                Objects.equals(getUsers(), group.getUsers()) &&
                Objects.equals(getPostfixId(), group.getPostfixId()) &&
                Objects.equals(getDepartmentId(), group.getDepartmentId()) &&
                Objects.equals(getSubjects(), group.getSubjects());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNumber(), getCourseNumber(), getUsers(), getPostfixId(), getDepartmentId(), getSubjects());
    }
}
