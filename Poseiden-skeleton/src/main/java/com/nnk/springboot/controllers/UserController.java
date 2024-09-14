package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.dto.*;
import com.nnk.springboot.service.*;
import jakarta.validation.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.validation.*;
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

            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            userService.addUser(userDTO);
            return "redirect:/admin/list";
        }
    }


    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {


        model.addAttribute("userDTO",userService.displayUserById(id));
        return "user/update";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid @ModelAttribute UserDTO userDTO,
                             BindingResult result) {

        if (result.hasErrors()) {
            return "user/update";
        }else {
            userService.updateUser(id, userDTO);
            return "redirect:/admin/list";
                }
        }



    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {

        userService.deleteUser(id);
        return "redirect:/admin/list";
    }
}
