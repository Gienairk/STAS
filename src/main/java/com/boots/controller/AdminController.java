package com.boots.controller;

import com.boots.entity.*;
import com.boots.repository.GroupRepository;
import com.boots.repository.SubjectRepository;
import com.boots.service.DatafinderService;
import com.boots.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private DatafinderService datafinderService;



    @GetMapping("/admin")
    public String admin(Model model){
        return "admin";
    }

    @GetMapping("/admin/adminUserList")
    public String userList(Model model

    ) {
        model.addAttribute("allUsers", userService.allUsers());
        return "adminUserList";
    }


    @RequestMapping("/admin/adminUserListPage/{pageNo}")
    public String userListPage(@PathVariable (value = "pageNo") int pageNo,Model model) {
        int pageSize=2;
        Page<User> page=userService.userList(pageNo,pageSize);
        List<User> userList=page.getContent();
        model.addAttribute("currentPage",pageNo);
        model.addAttribute("totalPages",page.getTotalPages());
        model.addAttribute("userList",userList);
        return "adminUserListPage";
    }
    /*
    @RequestMapping(value = "/admin/Groups/{id}",method = RequestMethod.GET)
    public String getGroup(Model model, @PathVariable("id") Long id) {
        Group group=datafinderService.findGroupbyId(id).orElseThrow(()-> new NullPointerException("в бд нет записи с данным id"));
        model.addAttribute("Group", group);
        return "Group";

    }*/

    @PostMapping("/admin/adminPostfix")
    public String  Postfix(@RequestParam(required = false, defaultValue = "" ) Long postfixId,
                           @RequestParam(required = true, defaultValue = "" ) String action,
                           @RequestParam(required = false, defaultValue = "" ) String postfixname,
                           @RequestParam(required = false, defaultValue = "" ) Long duration,
                           Model model) {
        if (action.equals("delete")){
            System.out.println("*-------------------------------*");
            System.out.println(postfixId);
            datafinderService.deletePostfix(postfixId);
        }
        if (action.equals("create")){
            datafinderService.createPostfix(postfixname,duration);
        }
        return "redirect:/admin/Postfix";

    }
    @PostMapping("/admin/adminSubject")
    public String  Subject(@RequestParam(required = false, defaultValue = "" ) Long subjectId,
                           @RequestParam(required = true, defaultValue = "" ) String action,
                           @RequestParam(required = false, defaultValue = "" ) String subjectname,
                           Model model) {
        if (action.equals("delete")){
            datafinderService.deleteSubject(subjectId);

        }
        if (action.equals("create")){
            datafinderService.createSubject(subjectname);
        }
        return "redirect:/admin/Subject";

    }

    @PostMapping("/admin/adminDepartment")
    public String  Department(@RequestParam(required = false, defaultValue = "" ) Long departmentId,
                           @RequestParam(required = true, defaultValue = "" ) String action,
                           @RequestParam(required = false, defaultValue = "" ) String departmentname,
                           Model model) {

        if (action.equals("delete")){
            datafinderService.deleteDepartment(departmentId);

        }
        if (action.equals("create")){
            datafinderService.createDepartment(departmentname);
        }
        return "redirect:/admin/Department";

    }

    @GetMapping("/admin/Subject")
    public String Subject(Model model) {
        model.addAttribute("allSubjects", datafinderService.allSubject());
        return "adminSubject";
    }
    @GetMapping("/admin/Postfix")
    public String Postfix(Model model) {
        model.addAttribute("allPostfix", datafinderService.allPostfix());
        return "adminPostfix";
    }

    @GetMapping("/admin/Department")
    public String Department(Model model) {
        model.addAttribute("allDepartment", datafinderService.allDepartment());
        return "adminDepartment";
    }

    @GetMapping("/admin/Group")
    public String Group(Model model) {
        model.addAttribute("allGroup", datafinderService.allGroup());
        model.addAttribute("allPostfix",datafinderService.allPostfix());
        model.addAttribute("allDepartment",datafinderService.allDepartment());
        return "adminGroup";
    }

    @GetMapping("/admin/ChooseSubject")
    public String ChooseSubject(Model model) {
        model.addAttribute("allSubject",datafinderService.allSubject());
        model.addAttribute("allTeather",datafinderService.allTeather());
        return "ChooseSubject";
    }

    @GetMapping("/admin/ChooseDepartment")
    public String ChooseDepartment(Model model) {
        model.addAttribute("allDepartment",datafinderService.allDepartment());
        model.addAttribute("allTeather",datafinderService.allTeather());
        return "ChooseDepartment";
    }
    @PostMapping("/admin/ChooseSubject")
    public String  ChooseDepartment(@RequestParam(required = false, defaultValue = "" ) String teather,
                                    @RequestParam(required = false,  defaultValue = "" ) String subject,
                                    Model model) {
        User user=datafinderService.findTeatherbyfullname(teather);
        Subject subject1=datafinderService.findSubjectbyname(subject);
        user.addSubject(subject1);
        datafinderService.createUser(user);
        return "redirect:/admin/ChooseSubject";
    }

    @GetMapping("/admin/ChooseGroup")
    public String ChooseGroup(Model model) {
        model.addAttribute("allGroup",datafinderService.allGroup());
        model.addAttribute("allStudents",datafinderService.allStudents());
        return "ChooseGroup";
    }

    @PostMapping("/admin/ChooseGroup")
    public String  ChooseGroup(@RequestParam(required = false, defaultValue = "" ) String student,
                               @RequestParam(required = false,  defaultValue = "" ) String group,
                               Model model) {
        User user=datafinderService.findStudentbyfullname(student);
        Group group1=datafinderService.findGroupbyFullName(group);
        user.addGroup(group1);
        datafinderService.createUser(user);
        return "redirect:/admin/ChooseGroup";
    }

    @PostMapping("/admin/ChooseDepartment")
    public String  GroupSubject(@RequestParam(required = false, defaultValue = "" ) String teather,
                                @RequestParam(required = false,  defaultValue = "" ) String department,
                                Model model) {
        User user=datafinderService.findTeatherbyfullname(teather);
        Department departmentobj=datafinderService.findDepartmentByName(department);
        user.addDepartment(departmentobj);
        datafinderService.createUser(user);
        return "redirect:/admin/ChooseDepartment";
    }

    @GetMapping("/admin/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());
        return "redirect:/registration";
    }

    @GetMapping("/admin/GroupSubject")
    public String SubjectforGroup(Model model) {
        model.addAttribute("allSG",datafinderService.allGroupWhereSubjectNotNull());
        model.addAttribute("allpostfix",datafinderService.allPostfix());
        model.addAttribute("allGroup", datafinderService.allGroup());
        model.addAttribute("allSubject",datafinderService.allSubject());
        return "adminGroupSubject";
    }

    @PostMapping("/admin/adminGroupSubject")
    public String  GroupSubject(@RequestParam(required = false, defaultValue = "" ) Long id,
                                @RequestParam(required = true,  defaultValue = "" ) String action,
                                @RequestParam(required = false, defaultValue = "" ) String subject,
                                @RequestParam(required = false, defaultValue = "" ) String group,
                                Model model) {
        System.out.println(subject);
        System.out.println(group);
        if (action.equals("create")){
            Subject subjectob=datafinderService.findSubjectbyname(subject);
            Group groupob=datafinderService.findGroupbyFullName(group);
            System.out.println(groupob.getId());
            System.out.println(groupob.getNumber());
            System.out.println(groupob.getCourseNumber());
            groupob.addSubject(subjectob);
            datafinderService.createGroup(groupob);
        }
        return "redirect:/admin/GroupSubject";
    }


    @PostMapping("/admin/adminGroup")
    public String  Group(@RequestParam(required = false, defaultValue = "" ) Long groupId,
                         @RequestParam(required = true,  defaultValue = "" ) String action,
                         @RequestParam(required = false, defaultValue = "" ) String groupNumber,
                         @RequestParam(required = false, defaultValue = "" ) Long coursenumber,
                         @RequestParam(required = false, defaultValue = "" ) String department,
                         @RequestParam(required = false, defaultValue = "" ) String postfix,
                         Model model) {
        if (action.equals("create")){
            Postfix postfixobj=datafinderService.findPostfixByName(postfix);
            Department departmentobj=datafinderService.findDepartmentByName(department);
            Group group=new Group(groupNumber,coursenumber,postfixobj,departmentobj,groupNumber+"-"+postfix);
            datafinderService.createGroup(group);
        }
        if (action.equals("delete")){
            datafinderService.deleteGroup(groupId);
        }
        return "redirect:/admin/Group";
    }

    @PostMapping("/admin/adminUserList")
    public String  deleteUser(@RequestParam(required = true, defaultValue = "" ) Long userId,
                              @RequestParam(required = true, defaultValue = "" ) String action,
                              Model model) {
        if (action.equals("delete")){
            userService.deleteUser(userId);
        }
        return "/admin/adminUserList";
    }

    @GetMapping("/admin/Groups")
    public String allGroups(Model model) {
        model.addAttribute("allGroup", datafinderService.allGroup());

        return "Groups";
    }

    @RequestMapping(value = "/admin/Groups/{id}",method = RequestMethod.GET)
    public String getGroup(Model model, @PathVariable("id") Long id) {
        Group group=datafinderService.findGroupbyId(id).orElseThrow(()-> new NullPointerException("в бд нет записи с данным id"));
        model.addAttribute("Group", group);
        return "Group";

    }
/*
    @GetMapping("/admin/adminUserList/gt/{userId}")
    public String  gtUser(@PathVariable("userId") Long userId, Model model) {
        model.addAttribute("allUsers", userService.usergtList(userId));
        return "/admin/adminUserList";
    }*/
}
