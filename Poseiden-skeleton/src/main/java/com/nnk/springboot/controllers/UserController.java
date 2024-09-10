package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.domain.dto.*;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.service.*;
import jakarta.validation.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("admin")
public class UserController {

   private final UserService userService;
   private final PasswordEncoder passwordEncoder;
   private final Utils utils;

    public UserController(UserService userService, PasswordEncoder passwordEncoder, Utils utils) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.utils = utils;
    }

    @GetMapping("/list")
    public String home(Model model)
    {
        model.addAttribute("users", userService.displayAllUser());
        model.addAttribute("connectedUser",utils.currentUser().getFullname());
        return "user/list";
    }

    @GetMapping("/add")
    public String addUser(Model model) {
        model.addAttribute("user",new UserDTO());
        return "user/add";
    }

    @PostMapping("/validate")
    public String validate(@Valid @ModelAttribute("user") UserDTO userDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/add";
        } else {
//            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//            user.setPassword(encoder.encode(user.getPassword()));
//            userRepository.save(user);
//            model.addAttribute("users", userRepository.findAll());
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            userService.addUser(userDTO);
            return "redirect:/admin/list";
        }
//        return "user/add";
    }


    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
//        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
//        user.setPassword("");
//        model.addAttribute("user", user);
//        return "user/update";
        System.out.println("this is yaser received id is "+ id);
        model.addAttribute("userDTO",userService.displayUserById(id));
        return "user/update";
    }

    @PostMapping("/update/{id}")//place holder for password doesn't work
    public String updateUser(@PathVariable("id") Integer id, @Valid @ModelAttribute UserDTO userDTO,
                             BindingResult result, Model model) {
        System.out.println("this is received id = "+ id);
        if (result.hasErrors()) {
            return "user/update";
        }else {
            userService.updateUser(id, userDTO);
            return "redirect:/admin/list";
                }
        }

//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        user.setPassword(encoder.encode(user.getPassword()));
//        user.setId(id);
//        userRepository.save(user);
//        model.addAttribute("users", userRepository.findAll());
//        return "redirect:/user/list";
//    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
//        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
//        userRepository.delete(user);
//        model.addAttribute("users", userRepository.findAll());
//        return "redirect:/user/list";
        userService.deleteUser(id);
        return "redirect:/admin/list";
    }
}
