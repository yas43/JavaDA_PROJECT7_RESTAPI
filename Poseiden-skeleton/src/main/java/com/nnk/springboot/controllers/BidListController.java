package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.dto.*;
import com.nnk.springboot.service.*;
import jakarta.validation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("bidList")
public class BidListController {
    private final BidListService bidListService;
    private final Utils utils;

    public BidListController(BidListService bidListService, Utils utils) {
        this.bidListService = bidListService;
        this.utils = utils;
    }
    // TODO: Inject Bid service

    @GetMapping("/list")
    public String home(Model model)
    {
        // TODO: call service find all bids to show to the view
        model.addAttribute("bidLists",bidListService.displayAllBidList());
        model.addAttribute("connectedUser",utils.currentUser().getFullname());
        return "bidList/list";
    }

    @GetMapping("/add")
    public String addBidForm(Model model) {
        model.addAttribute("bidList",new BidListDTO());
        return "bidList/add";
    }

    @PostMapping("/validate")
    public String validate(@Valid @ModelAttribute("bidList") BidListDTO bidListDTO, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return bid list

        if (result.hasErrors()){
            return "bidList/add";
        }
        else {
             bidListService.addBidList(bidListDTO);
        }

        return "redirect:/bidList/list";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get Bid by Id and to model then show to the form
        if (bidListService.isExist(id)) {
            model.addAttribute(bidListService.displayBidListById(id));
            return "bidList/update";
        }
        else return "redirect;/bidList/update";
    }

    @PostMapping("/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidListDTO bidListDTO,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Bid and return list Bid
        model.addAttribute("bidLists",bidListService.updateBidList( id,bidListDTO));
        return "redirect:/bidList/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Bid by Id and delete the bid, return to Bid list
        bidListService.deleteBidList(id);
        model.addAttribute("bidLists",bidListService.displayAllBidList());
        return "bidList/list";
    }
}
