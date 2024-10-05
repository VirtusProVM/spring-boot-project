package com.jobportal.controller;

import com.jobportal.model.Users;
import com.jobportal.model.WorkerType;
import com.jobportal.service.UsersService;
import com.jobportal.service.WorkerTypeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    private final WorkerTypeService workerTypeService;
    private final UsersService usersService;

    @Autowired
    public UserController(WorkerTypeService workerTypeService, UsersService usersService) {
        this.workerTypeService = workerTypeService;
        this.usersService = usersService;
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        List<WorkerType> types = workerTypeService.getAll();

        model.addAttribute("types", types);
        model.addAttribute("user", new Users());

        return "signup";
    }

    @PostMapping("/signup/newUser")
    public String registerUser(@Valid Users users, Model model) {

        Optional<Users> op = usersService.findByEmail(users.getEmail());

        if(op.isPresent()) {
            model.addAttribute("error", "Email already registered,try to login or register with other email.");
            List<WorkerType> types = workerTypeService.getAll();

            model.addAttribute("types", types);
            model.addAttribute("user", new Users());

            return "signup";
        }

        usersService.createNewUser(users);

        return "redirect:/job-board/";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        return "redirect:/";
    }
}
