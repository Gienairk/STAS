package com.boots.service;

import com.boots.entity.*;

import com.boots.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class DatafinderService {
    @Autowired
    PostfixRepository postfixRepository;
    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    UserRepository userRepository;




    public List<Postfix> allPostfix() {
        return postfixRepository.findAll();
    }
    public List<Subject> allSubject(){
        return subjectRepository.findAll();
    }
    public List<User> allUser(){return  userRepository.findAll();}
    public List<Department> allDepartment() {
        return departmentRepository.findAll();
    }

    public Postfix findPostfixByName(String name){
        return postfixRepository.findByName(name);
    }

    public Group findGroupbyFullName(String name){
        return groupRepository.findByFullname(name);
    }
    public Optional<Group> findGroupbyId(Long id){
        return groupRepository.findById(id);
    }

    public List<Group> allGroup() {
        return groupRepository.findAll();
    }

    public Subject findSubjectbyname(String name){
        return subjectRepository.findByName(name);
    }

    public Department findDepartmentByName(String name){
        return departmentRepository.findByDepartmentName(name);
    }

    public List<User> allTeather(){return userRepository.findAllByUserRightEquals("Teather"); }

    public List<User> allStudents(){return userRepository.findAllByUserRightEquals("Student"); }

    public boolean deletePostfix(Long postfixId) {
        if (postfixRepository.findById(postfixId).isPresent()) {
            postfixRepository.deleteById(postfixId);
            return true;
        }
        return false;
    }

    public void createPostfix(String postfixname, Long duration) {
        Postfix postfix=new Postfix(postfixname,duration);
        System.out.println("postfix "+postfix.toString());
        postfixRepository.save(postfix);
    }

    public void createGroup(Group group) {
        groupRepository.save(group);
    }

    public void createUser(User user){
        userRepository.save(user);
    }


    public boolean deleteSubject(Long subjectId) {
        if (subjectRepository.findById(subjectId).isPresent()) {
            subjectRepository.deleteById(subjectId);
            return true;
        }
        return false;
    }

    public boolean deleteDepartment(Long departmentId) {
        if (departmentRepository.findById(departmentId).isPresent()) {
            departmentRepository.deleteById(departmentId);
            return true;
        }
        return false;
    }

    public void createSubject(Subject subjectname) {
        subjectRepository.save(subjectname);
    }

    public void createSubject(String subjectname) {
        Subject subject=new Subject(subjectname);
        subjectRepository.save(subject);
    }
    public void createDepartment(String departmentname) {
        Department department=new Department(departmentname);
        departmentRepository.save(department);
    }


    public boolean deleteGroup(Long groupId) {
        if (groupRepository.findById(groupId).isPresent()) {
            groupRepository.deleteById(groupId);
            return true;
        }
        return false;
    }

    public Set<Group> allGroupWhereSubjectNotNull() {
        return groupRepository.findAllBySubjectsNotNull();
    }

    public User findTeatherbyfullname(String teather) {
        String[] name=teather.split(" ");
        return userRepository.findByFirstNameAndSecondNameAndLastNameAndUserRight(name[0],name[1],name[2],"Teather");
    }
    public User findStudentbyfullname(String student) {
        String[] name=student.split(" ");
        return userRepository.findByFirstNameAndSecondNameAndLastNameAndUserRight(name[0],name[1],name[2],"Student");
    }
}
