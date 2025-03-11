package ru.kata.spring.boot_security.demo.controller;


import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.dao.RoleRepo;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    private final RoleRepo roleRepo;

    public UserController(UserService userService, RoleRepo roleRepo) {
        this.userService = userService;
        this.roleRepo = roleRepo;
    }

    @GetMapping(value = "/user")
    public String printWelcome(Model model) {
        Authentication auth = SecurityContextHolder.getContext ().getAuthentication ();
        String username = auth.getName ();
        User user = userService.findUserByUsername (username);
        model.addAttribute ("user", user);
        return "userPage";
    }

    @GetMapping(value = "/admin")
    public String printWelcomeForAdmin(Model model) {
        List<User> allUsers = userService.showUsers ();
        model.addAttribute ("users", allUsers);
        return "allUsersForAdmin";
    }

    @GetMapping(value = "/addUser")
    public String showUserInfo(Model model) {
        model.addAttribute ("user", new User ());
        model.addAttribute ("roles", roleRepo.findAll ());
        return "userFormForAdd";
    }

    @PostMapping(value = "/addUser")
    public String addUser(@Valid @ModelAttribute User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors ()) {
            return "userFormForAdd";
        }
        userService.addUser (user);
        return "redirect:/admin";
    }

    @PostMapping(value = "/updateUser")
    public String updateUser(@Valid @ModelAttribute User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors ()) {
            return "userFormForUpdate";
        }
        userService.updateUser (user);
        return "redirect:/admin";
    }

    @GetMapping("/editUser")
    public String updateInfo(@RequestParam("id") int id, Model model) {
        User user = userService.getUserById (id);
        model.addAttribute ("user", user);
        model.addAttribute ("roles", roleRepo.findAll ());
        return "userFormForUpdate";
    }

    @GetMapping("deleteUser")
    public String deleteUser(@RequestParam("id") int id) {
        userService.deleteUser (id);
        return "redirect:/admin";
    }

}