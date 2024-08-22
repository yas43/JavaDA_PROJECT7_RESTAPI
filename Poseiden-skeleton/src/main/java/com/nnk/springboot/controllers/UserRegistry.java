package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.*;
import com.nnk.springboot.domain.dto.*;
import com.nnk.springboot.repositories.*;
import org.springframework.http.*;
import org.springframework.security.crypto.password.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class UserRegistry {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserRegistry(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @PostMapping("adduser")
    public User registerUser(@RequestBody UserDTO userDTO){
//        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setFullname(userDTO.getFullname());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(userDTO.getRole());
        return new ResponseEntity<>(userRepository.save(user),HttpStatus.OK).getBody();
    }

}
