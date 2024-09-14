package com.nnk.springboot.controllers;

import com.nnk.springboot.service.*;
import jakarta.servlet.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;

@Controller
@RequestMapping("app")
public class LoginController {
    private final Utils utils;

    public LoginController(Utils utils) {
        this.utils = utils;
    }

    //

    @GetMapping("error")
    public ModelAndView error() {
        ModelAndView mav = new ModelAndView();
        String errorMessage= "You are not authorized for the requested data.";
        mav.addObject("errorMsg", errorMessage);
        mav.addObject("connectedUser",utils.currentUser().getFullname());
        mav.setViewName("403");
        return mav;
    }


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
