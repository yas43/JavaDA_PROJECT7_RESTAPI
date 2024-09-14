package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.dto.*;
import com.nnk.springboot.service.*;
import jakarta.validation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("rating")
public class RatingController {

    private final RatingService ratingService;
    private final Utils utils;

    public RatingController(RatingService ratingService, Utils utils) {
        this.ratingService = ratingService;
        this.utils = utils;
    }

    @GetMapping("/list")
    public String home(Model model)
    {

        model.addAttribute("ratings", ratingService.displayAllRating());
        model.addAttribute("connectedUser",utils.currentUser().getFullname());
        return "rating/list";
    }

    @GetMapping("/add")
    public String addRatingForm(Model model) {
        model.addAttribute("rating",new RatingDTO());
        return "rating/add";
    }

    @PostMapping("/validate")
    public String validate(@Valid @ModelAttribute("rating") RatingDTO ratingDTO, BindingResult result) {
        if (result.hasErrors()){
            return "rating/add";
        }
        else {
            ratingService.addRating(ratingDTO);
            return "redirect:/rating/list";
        }
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("rating",ratingService.displayRatingById(id));
        return "rating/update";
    }

    @PostMapping("/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid RatingDTO ratingDTO,
                             BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/rating/list";
        }
        else {
            ratingService.updateRating(id,ratingDTO);
            return "redirect:/rating/list";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id) {
        ratingService.deleteRating(id);
        return "redirect:/rating/list";
    }
}
