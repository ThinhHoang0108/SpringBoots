package com.hellospring.demoproject.controller;

import com.hellospring.demoproject.enity.Role;
import com.hellospring.demoproject.enity.User;
import com.hellospring.demoproject.service.UserNotFoundException;
import com.hellospring.demoproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService service;

    @GetMapping("/users")
    public String listAll(Model model) {
        List<User> userList = service.listAll();
        model.addAttribute("listUsers", userList);
        return "users";
    }

    @GetMapping("/users/newuser")
    public String newUser(Model model) {
        List<Role> roleList = service.roleList();
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("listRole", roleList);
        model.addAttribute("pageTitle", "Create New User");
        return "createusers";
    }

    @PostMapping("/users/save")
    public String saveUser(User user, RedirectAttributes redirectAttributes) {
        System.out.println(user);
        service.save(user);
        redirectAttributes.addFlashAttribute("message", "The user has been saved success");
        return "redirect:/users";
    }

    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable(name = "id") Integer id,
                           RedirectAttributes redirectAttributes,
                           Model model) {
        try {
            User user = service.get(id);
            List<Role> roleList = service.roleList();
            model.addAttribute("user", user);
            model.addAttribute("listRole", roleList);
            model.addAttribute("pageTitle", "Edit User (ID: " + id + ")");
            return "createusers";

        } catch (UserNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
            return "redirect:/users";
        }
    }
    //cai nay de test commit
    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable(name = "id") Integer id,
                             RedirectAttributes redirectAttributes,
                             Model model) {
        try {
            service.delete(id);
            redirectAttributes.addFlashAttribute("message", "The userID=" + id + " has been deleted");
        } catch (UserNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
        }
        return "redirect:/users";

    }

    @GetMapping("/users/{id}/enabled/{status}")
    public String updateUserEnabledStatus(@PathVariable("id") Integer id,
                                          @PathVariable("status") boolean enabled,
                                          RedirectAttributes redirectAttributes) {
        service.updateUserEnabledStatus(id, enabled);
        String status = enabled ? "enabled" : "disabled";
        String message = "The user ID " + id + " has been " + status;
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/users";
    }
}
