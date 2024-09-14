package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.dto.*;
import com.nnk.springboot.service.*;
import jakarta.validation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("curvePoint")
public class CurveController {

    private final CurvePointService curvePointService;
    private final Utils utils;

    public CurveController(CurvePointService curvePointService, Utils utils) {
        this.curvePointService = curvePointService;
        this.utils = utils;
    }

    @GetMapping("/list")
    public String home(Model model)
    {

        model.addAttribute("curvePoints",curvePointService.displayAllCurvePoint());
        model.addAttribute("connectedUser",utils.currentUser().getFullname());
        return "curvePoint/list";
    }

    @GetMapping("/add")
    public String addBidForm(Model model) {
        model.addAttribute("curvePoint",new CurvePointDTO());
        return "curvePoint/add";
    }

    @PostMapping("/validate")
    public String validate(@Valid @ModelAttribute("curvePoint") CurvePointDTO curvePointDTO, BindingResult result) {
        if (result.hasErrors()){
            return "curvePoint/add";
        }
        else {
            curvePointService.addCurvePoint(curvePointDTO);
            return "redirect:/curvePoint/list";
        }
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("curvePoints",curvePointService.displayCurvePointById(id));
        return "curvePoint/update";
    }

    @PostMapping("/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePointDTO curvePointDTO,
                             BindingResult result) {
        if (result.hasErrors()){
            return "redirect:/curvePoint/update";
        }else {
            curvePointService.updateCurvePoint(id, curvePointDTO);
            return "redirect:/curvePoint/list";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id) {
        curvePointService.deleteCurvPoint(id);
        return "redirect:/curvePoint/list";
    }
}
