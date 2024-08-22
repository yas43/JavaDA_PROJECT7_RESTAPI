package com.nnk.springboot.controllers;

import com.nnk.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("app")
public class LoginController {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @GetMapping("login")
//    public ModelAndView login() {
//        ModelAndView mav = new ModelAndView();
//        mav.setViewName("login");
//        return mav;
//    }
//
//    @GetMapping("secure/article-details")
//    public ModelAndView getAllUserArticles() {
//        ModelAndView mav = new ModelAndView();
//        mav.addObject("users", userRepository.findAll());
//        mav.setViewName("user/list");
//        return mav;
//    }
//
//    @GetMapping("error")
//    public ModelAndView error() {
//        ModelAndView mav = new ModelAndView();
//        String errorMessage= "You are not authorized for the requested data.";
//        mav.addObject("errorMsg", errorMessage);
//        mav.setViewName("403");
//        return mav;
//    }

        @PostMapping("/api/login")
    public String login(@RequestParam String username,@RequestParam String password){
//        System.out.println("hello this is yaser");
//        customUserDetailsService.loadUserByUsername(username);
//        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username,password);
//        Authentication authentication = authenticationManager.authenticate(authToken);
//        SecurityContextHolder.getContext().setAuthentication(authentication);


        return "user logged successfully";
    }


}
