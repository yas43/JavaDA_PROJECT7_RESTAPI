package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.dto.*;
import com.nnk.springboot.service.*;
import jakarta.validation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("trade")
public class TradeController {

    private final TradeService tradeService;
    private final Utils utils;

    public TradeController(TradeService tradeService, Utils utils) {
        this.tradeService = tradeService;
        this.utils = utils;
    }

    @GetMapping("/list")
    public String home(Model model)
    {

        model.addAttribute("trades",tradeService.displayAllTrade());
        model.addAttribute("connectedUser",utils.currentUser().getFullname());
        return "trade/list";
    }

    @GetMapping("/add")
    public String addUser(Model model) {
        model.addAttribute("trade",new TradeDTO());
        return "trade/add";
    }

    @PostMapping("/validate")
    public String validate(@Valid @ModelAttribute("trade") TradeDTO tradeDTO, BindingResult result) {

        if (result.hasErrors()) {
            return "trade/add";
        }
        else {
            tradeService.addTrade(tradeDTO);
            return "redirect:/trade/list";
        }
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        model.addAttribute("trade",tradeService.displayTradeById(id));
        return "trade/update";
    }

    @PostMapping("/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid TradeDTO tradeDTO,
                             BindingResult result) {

        if (result.hasErrors()) {
            return "redirect:/trade/update";
        }else {
            tradeService.updateTrade(id,tradeDTO);
            return "redirect:/trade/list";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id) {

        tradeService.deleteTrade(id);
        return "redirect:/trade/list";
    }
}
