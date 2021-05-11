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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private DatafinderService datafinderService;

    final int pageSize=10;

    @GetMapping("/admin")
    public String admin(Model model){
        return "admin";
    }

    /*
    @GetMapping("/admin/adminUserList")
    public String userList(Model model) {
        model.addAttribute("allUsers", userService.allUsers());
        return "adminUserList";
    }*/


    @RequestMapping("/admin/SubjectPage/{pageNo}")
    public String subjectPage(@PathVariable (value = "pageNo") int pageNo,Model model) {
        Page<Subject>page=datafinderService.subjectList(pageNo,pageSize);
        List<Subject>subjectList=page.getContent();
        model.addAttribute("currentPage",pageNo);
        model.addAttribute("totalPages",page.getTotalPages());
        model.addAttribute("subjectList",subjectList);
        /*Page<User> page=userService.userList(pageNo,pageSize);
        List<User> userList=page.getContent();
        model.addAttribute("currentPage",pageNo);
        model.addAttribute("totalPages",page.getTotalPages());
        model.addAttribute("userList",userList);*/
        return "subjectPage";
    }

    @PostMapping("/admin/SubjectPage/{pageNo}")
    public String actionSubjectInBase(@PathVariable (value = "pageNo") int pageNo,
                                     @RequestParam(required = false, defaultValue = "" ) String name,
                                     @RequestParam(required = false, defaultValue = "" ) Long subjectId,
                                     @RequestParam(required = true, defaultValue = "" ) String action,
                                     RedirectAttributes redirectAttrs,
                                     Model model) {
        if (action.equals("delete")){
            datafinderService.deleteSubjectFromBase(subjectId);
        }
        if (action.equals("find")){
            if (name.equals(""))
                return "redirect:/admin/SubjectPage/"+pageNo;
            List<Subject>subjects=new ArrayList<>();
            subjects.add(datafinderService.findSubjectbyname(name));
            if (subjects.get(0)==null)
                return "redirect:/admin/SubjectPage/"+pageNo;
            redirectAttrs.addFlashAttribute("subject",subjects);
        }
        return "redirect:/admin/SubjectPage/"+pageNo;

    }


    @RequestMapping("/admin/adminUserListPage/{pageNo}")
    public String userListPage(@PathVariable (value = "pageNo") int pageNo,Model model) {
        Page<User> page=datafinderService.userList(pageNo,pageSize);
        List<User> userList=page.getContent();
        model.addAttribute("currentPage",pageNo);
        model.addAttribute("totalPages",page.getTotalPages());
        model.addAttribute("userList",userList);
        return "adminUserListPage";
    }


    @PostMapping("/admin/adminUserListPage/{pageNo}")
    public String actionUserInBase(@PathVariable (value = "pageNo") int pageNo,
                                      @RequestParam(required = false, defaultValue = "" ) String name,
                                      @RequestParam(required = false, defaultValue = "" ) String surname,
                                      @RequestParam(required = false, defaultValue = "" ) String patronymic,
                                      @RequestParam(required = false, defaultValue = "" ) Long userId,
                                      @RequestParam(required = true, defaultValue = "" ) String action,
                                      RedirectAttributes redirectAttrs,
                                      Model model) {
        if (action.equals("delete")){
            datafinderService.deleteTeather(userId);
            datafinderService.deleteUserFromGroups(userId);
            datafinderService.deleteUserFromChats(userId);
            datafinderService.deleteUser(userId);
        }
        if (action.equals("find")){
            List<User>users=datafinderService.findUserBySomeName(name,surname,patronymic);
            redirectAttrs.addFlashAttribute("users",users);
          //  redirectAttrs.addFlashAttribute("haveSomeUsers",1);
        }
        return "redirect:/admin/adminUserListPage/"+pageNo;
        //return new RedirectView("/admin/adminUserListPage/"+pageNo);
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

    @GetMapping("/admin/GroupCreate")
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
        if (action.equals("create")){
            Subject subjectob=datafinderService.findSubjectbyname(subject);
            Group groupob=datafinderService.findGroupbyFullName(group);
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
        /*if (action.equals("delete")){
            datafinderService.deleteGroup(groupId);
        }*/
        return "redirect:/admin/GroupCreate";
    }
/*
    @PostMapping("/admin/adminUserList")
    public String  deleteUser(@RequestParam(required = true, defaultValue = "" ) Long userId,
                              @RequestParam(required = true, defaultValue = "" ) String action,
                              Model model) {
        if (action.equals("delete")){
            userService.deleteUser(userId);
        }
        return "/admin/adminUserList";
    }*/
/*
    @GetMapping("/admin/Groups")
    public String allGroups(Model model) {
        model.addAttribute("allGroup", datafinderService.allGroup());
        return "Groups";
    }
*/

    @RequestMapping("/admin/PostfixPage/{pageNo}")
    public String allPostfix(@PathVariable (value="pageNo") int pageNo,Model model) {
        Page<Postfix> page=datafinderService.postfixList(pageNo,pageSize);
        List<Postfix> postfixList=page.getContent();
        model.addAttribute("currentPage",pageNo);
        model.addAttribute("totalPages",page.getTotalPages());
        model.addAttribute("postfixList",postfixList);
        return "postfix";
    }

    @PostMapping("/admin/PostfixPage/{pageNo}")
    public String actionPostfixInBase(@PathVariable (value = "pageNo") int pageNo,
                                   @RequestParam(required = false, defaultValue = "" ) String name,
                                   @RequestParam(required = false, defaultValue = "" ) Long id,
                                   @RequestParam(required = true, defaultValue = "" ) String action,
                                   RedirectAttributes redirectAttrs,
                                   Model model) {
        if (action.equals("delete")){
           datafinderService.deletePostfix(id);//для удаляния в начале надо убрать все связанные группы
        }
        if (action.equals("find")){
            List<Postfix>postfixList=datafinderService.findPostfix(name);
            redirectAttrs.addFlashAttribute("postfix",postfixList);
        }
        return "redirect:/admin/PostfixPage/"+pageNo;
    }

    @RequestMapping("/admin/Groups/{pageNo}")
    public String allGroups(@PathVariable (value="pageNo") int pageNo,Model model) {
       // model.addAttribute("allGroup", datafinderService.allGroup());
        Page<Group> page=datafinderService.groupsList(pageNo,pageSize);
        List<Group> groupList=page.getContent();
        model.addAttribute("currentPage",pageNo);
        model.addAttribute("totalPages",page.getTotalPages());
        model.addAttribute("groupList",groupList);
        return "Groups";
}

    @PostMapping("/admin/Groups/{pageNo}")
    public String findGroup(@PathVariable (value = "pageNo") int pageNo,
                                   @RequestParam(required = false, defaultValue = "" ) String groupNumber,
                                   @RequestParam(required = false, defaultValue = "" ) String groupPostfix,
                                   @RequestParam(required = true,  defaultValue = "" ) String action,
                                   RedirectAttributes redirectAttrs,
                                   Model model) {
        if (action.equals("findGroup")){
        List<Group>groupRez=datafinderService.findGroupByName(groupNumber,groupPostfix);
        redirectAttrs.addFlashAttribute("groupRez",groupRez);
        }
        if (action.equals("upAllGroup")){
            datafinderService.upAllGroup();
        }
        return "redirect:/admin/Groups/"+pageNo;
        //return new RedirectView("/admin/adminUserListPage/"+pageNo);
    }
    @RequestMapping(value = "/admin/Groups/Group/{id}",method = RequestMethod.GET)
    public String getGroup(Model model, @PathVariable("id") Long id) {
        Group group=datafinderService.findGroupbyId(id).orElseThrow(()-> new NullPointerException("в бд нет записи с данным id"));
        model.addAttribute("Group", group);
        return "Group";
    }
    @PostMapping("/admin/Groups/Group/{id}")
    public String  GroupDataChanger(
                         @RequestParam(required = true,  defaultValue = "" ) String action,
                         @RequestParam(required = true,  defaultValue = "" ) Long dataId,
                         @PathVariable("id") Long id,
                         Model model) {
        if (action.equals("deleteSubject")){
            datafinderService.deleteSubjectFromGroup(id,dataId);
        }
        if (action.equals("deleteUser")){
            datafinderService.deleteUserFromGroup(id,dataId);
        }
        if (action.equals("deleteGroup")){
            datafinderService.deleteGroupFromDB(id);
            return "redirect:/admin/Groups/1";
        }
        if (action.equals("upGroup")){
            datafinderService.upGroup(id);
            return "redirect:/admin/Groups/1";
        }

        return "redirect:/admin/Groups/Group/"+id;
    }
/*
    @GetMapping("/admin/adminUserList/gt/{userId}")
    public String  gtUser(@PathVariable("userId") Long userId, Model model) {
        model.addAttribute("allUsers", userService.usergtList(userId));
        return "/admin/adminUserList";
    }*/
}
