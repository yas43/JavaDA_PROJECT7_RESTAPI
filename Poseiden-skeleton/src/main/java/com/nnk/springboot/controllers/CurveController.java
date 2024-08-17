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
    // TODO: Inject Curve Point service
    private final CurvePointService curvePointService;

    public CurveController(CurvePointService curvePointService) {
        this.curvePointService = curvePointService;
    }

    @GetMapping("/list")
    public String home(Model model)
    {
        // TODO: find all Curve Point, add to model
        model.addAttribute("curvePoints",curvePointService.displayAllCurvePoint());
        return "curvePoint/list";
    }

    @GetMapping("/add")
    public String addBidForm(Model model) {
        model.addAttribute("curvePoint",new CurvePointDTO());
        return "curvePoint/add";
    }

    @PostMapping("/validate")
    public String validate(@Valid @ModelAttribute("curvePoint") CurvePointDTO curvePointDTO, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return Curve list
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
        // TODO: get CurvePoint by Id and to model then show to the form
        model.addAttribute("curvePoints",curvePointService.displayCurvePointById(id));
        return "curvePoint/update";
    }

    @PostMapping("/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePointDTO curvePointDTO,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Curve and return Curve list
        if (result.hasErrors()){
            return "redirect:/curvePoint/update";
        }else {
            curvePointService.updateCurvePoint(id, curvePointDTO);
//            model.addAttribute("curvePoints",curvePointService.updateCurvePoint(id, curvePointDTO));
            return "redirect:/curvePoint/list";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Curve by Id and delete the Curve, return to Curve list
        return "redirect:/curvePoint/list";
    }
}
