package com.boots.service;

import com.boots.entity.*;

import com.boots.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    @Autowired
    UserChatRoomRepository userChatRoomRepository;




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
    public boolean deleteSubjectFromBase(Long subjectName) {
        if (subjectRepository.findById(subjectName)!=null) {
            Optional<Subject> subjectOptional=subjectRepository.findById(subjectName);
            Subject subject=subjectOptional.get();
            deleteSubjectFromGroup(subject);
            deleteSubjectFromTeathers(subject);
            subjectRepository.delete(subject);
            /*System.out.println("+++++" +subject.getGroups());
            subject.getGroups().clear();
            System.out.println("-----" +subject.getGroups());
            subject.getUsers().clear();
            subjectRepository.save(subject);*/
            return true;
        }
        return false;
    }
    public void deleteSubjectFromGroup(Subject subject){
        List<Group>groups=groupRepository.findAllBySubjects(subject);
        for (int i = 0; i <groups.size() ; i++) {
            groups.get(i).getSubjects().remove(subject);
            groupRepository.save(groups.get(i));
        }
    }
    public void deleteSubjectFromTeathers(Subject subject){
        List<User>userList=userRepository.findAllBySubjects(subject);
        for (int i = 0; i <userList.size() ; i++) {
            userList.get(i).getSubjects().remove(subject);
            userRepository.save(userList.get(i));
        }
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

    public void deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            System.out.println("удаляю "+userId);
            //Optional<User> user=userRepository.findById(userId);
            userRepository.deleteById(userId);
        }
    }
    public void deleteGroupFromDB(Long groupId) {
        if (groupRepository.findById(groupId).isPresent()){
            Optional<Group> groupOb = groupRepository.findById(groupId);
            Group group=groupOb.get();
            group.getSubjects().clear();

            List<User>users=new ArrayList<>(group.getUsers());
            for (int i = 0; i <users.size() ; i++) {
                deleteUserFromGroup(groupId,users.get(i).getId());
            }

            groupRepository.delete(group);
        }
    }

    public void deleteSubjectFromGroup(Long groupId,Long subjectId) {
        Optional<Group> groupOptional=groupRepository.findById(groupId);
        Group group=groupOptional.get();
        Optional<Subject> subjectOptional=subjectRepository.findById(subjectId);
        Subject subject=subjectOptional.get();
        group.getSubjects().remove(subject);
        groupRepository.save(group);
        //Optional<Subject> subject=subjectRepository.findById(subjectId);
        //group.get().getSubjects().remove(subject);
        //groupRepository.save(group.get());
    }

    public void deleteUserFromGroup(Long groupId, Long userId) {
        Optional<Group> groupOptional=groupRepository.findById(groupId);
        Group group=groupOptional.get();
        Optional<User> userOptional=userRepository.findById(userId);
        User user=userOptional.get();
        user.getGroups().remove(group);
        userRepository.save(user);
    }

    public void deleteUserFromGroups(Long userId) {
        Optional<User> userOptional=userRepository.findById(userId);
        User user=userOptional.get();
        user.getGroups().clear();
        userRepository.save(user);


    }
    public void deleteUserFromChats(Long userId) {
        Optional<User> userOptional=userRepository.findById(userId);
        User user=userOptional.get();
        List<UserChatRoom> chatRooms=userChatRoomRepository.getAllByUser(user);
        for (int i = 0; i <chatRooms.size() ; i++) {
            userChatRoomRepository.delete(chatRooms.get(i));
        }
        //user.getUserChatRooms().clear();
        //userRepository.save(user);

    }
    public void deleteTeather(Long userId) {
        Optional<User> userOptional=userRepository.findById(userId);
        User user=userOptional.get();
        user.getSubjects().clear();
        user.getDepartments().clear();
        userRepository.save(user);
    }
    public Page<User> userList(int pageNo, int pageSize) {
        Pageable pageable= PageRequest.of(pageNo -1,pageSize);
        return this.userRepository.findAll(pageable);
    }

    public Page<Subject> subjectList(int pageNo, int pageSize) {
        Pageable pageable= PageRequest.of(pageNo -1,pageSize);
        return this.subjectRepository.findAll(pageable);
    }

    public Page<Group> groupsList(int pageNo, int pageSize) {
        Pageable pageable= PageRequest.of(pageNo -1,pageSize);
        return this.groupRepository.findAll(pageable);
    }

    public Page<Postfix> postfixList(int pageNo, int pageSize) {
        Pageable pageable= PageRequest.of(pageNo -1,pageSize);
        return this.postfixRepository.findAll(pageable);
    }

    public List<User> findUserBySomeName(String firstName,String lastName,String secondName){
        List<User> userList=null;
        boolean firstNameNull= (firstName.equals(""));//firstName
        boolean lastNameNull= (lastName.equals(""));//lastName
        boolean secondNameNull= (secondName.equals(""));//secondName
        if (firstNameNull && lastNameNull && secondNameNull){
            userList=userRepository.findAll();
        }
        if (firstNameNull && lastNameNull && !secondNameNull){
            userList=userRepository.findAllBySecondName(secondName);
        }
        if (firstNameNull && !lastNameNull && secondNameNull){
            userList=userRepository.findAllByLastName(lastName);
        }
        if (firstNameNull && !lastNameNull && !secondNameNull){
            userList=userRepository.findAllByLastNameAndSecondName(lastName,secondName);
        }
        if (!firstNameNull && lastNameNull && secondNameNull){
            userList=userRepository.findAllByFirstName(firstName);
        }
        if (!firstNameNull && lastNameNull && !secondNameNull){
            userList=userRepository.findAllByFirstNameAndSecondName(firstName,secondName);
        }
        if (!firstNameNull && !lastNameNull && secondNameNull){
            userList=userRepository.findAllByLastNameAndFirstName(lastName,firstName);
        }
        if (!firstNameNull && !lastNameNull && !secondNameNull){
            userList=userRepository.findAllByFirstNameAndSecondNameAndLastName(firstName,secondName,lastName);
        }
        return userList;
    }
    public List<Group> findGroupByName(String groupNumber, String groupPostfix) {
        String fullnameFinder=groupNumber+"-"+groupPostfix;
        return groupRepository.getAllByFullnameContains(fullnameFinder);


    }


    public List<Postfix> findPostfix(String name) {
        return postfixRepository.getAllByName(name);
    }

    public void upGroup(Long id) {
        Optional<Group> groupOptional=groupRepository.findById(id);
        Group group=groupOptional.get();
        group.upCourse();
        if (group.getCourseNumber()>group.getPostfixId().getDuration())
            deleteGroupFromDB(id);
        else
            groupRepository.save(group);
    }
    public void upGroup(Group group) {
        group.upCourse();
        if (group.getCourseNumber()>group.getPostfixId().getDuration()) {
            List<User> userList=new ArrayList<>(group.getUsers());
            Long id;
            for (int i = 0; i <userList.size() ; i++) {
                id=userList.get(i).getId();
                deleteUserFromGroups(id);
                //deleteUserFromChats(id);
               // deleteUser(id);
            }
            groupRepository.delete(group);
        }
        else
            groupRepository.save(group);
    }

    public void upAllGroup() {
        List<Group> groupList=groupRepository.findAll();
        for (int i = 0; i <groupList.size() ; i++) {
            upGroup(groupList.get(i));
        }
    }
}
