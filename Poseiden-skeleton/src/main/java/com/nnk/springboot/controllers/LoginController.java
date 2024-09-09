package com.nnk.springboot.controllers;

import com.nnk.springboot.repositories.UserRepository;
import jakarta.servlet.http.*;
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


    @GetMapping("/default")
    public String defaultSuccessfulHandler(HttpServletRequest request){
        if (request.isUserInRole("ADMIN")){
            return "redirect:/admin/list";
        } else if (request.isUserInRole("USER")) {
            return "redirect:/bidList/list";
        }else {
            return "/login";
        }
    }

        @PostMapping("/api/login")
    public String login(@RequestParam String username,@RequestParam String password){

        return "user logged successfully";
    }


}
