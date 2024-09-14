package com.nnk.springboot.service;

import com.nnk.springboot.domain.*;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.*;
import org.springframework.security.core.*;
import org.springframework.security.core.context.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class Utils {
    private final UserRepository userRepository;

    public Utils(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * give user who is login
     * @return existing user in DB who is login
     */
    public User currentUser(){
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       Object principle = authentication.getPrincipal();
       UserDetails userDetails = (UserDetails) principle;
       var currentuser = userRepository.findByUsername(userDetails.getUsername())
               .orElseThrow(()->new RuntimeException(" user not founded"));
       return currentuser;
   }
}
