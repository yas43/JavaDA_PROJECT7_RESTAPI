package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.dto.*;
import com.nnk.springboot.service.*;
import jakarta.validation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("ruleName")
public class RuleNameController {

    private final RuleNameService ruleNameService;
    private final Utils utils;

    public RuleNameController(RuleNameService ruleNameService, Utils utils) {
        this.ruleNameService = ruleNameService;
        this.utils = utils;
    }

    @GetMapping("/list")
    public String home(Model model)
    {

        model.addAttribute("ruleNames",ruleNameService.displayAllRuleName());
        model.addAttribute("connectedUser",utils.currentUser().getFullname());
        return "ruleName/list";
    }

    @GetMapping("/add")
    public String addRuleForm(Model model) {
        model.addAttribute("ruleName",new RulleNameDTO());
        return "ruleName/add";
    }

    @PostMapping("/validate")
    public String validate(@Valid @ModelAttribute("ruleName") RulleNameDTO rulleNameDTO, BindingResult result) {

        if (result.hasErrors()) {
            return "ruleName/add";
        }else {
            ruleNameService.addRuleName(rulleNameDTO);
            return "redirect:/ruleName/list";
        }
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        model.addAttribute("ruleName",ruleNameService.displayruleNameById(id));
        return "ruleName/update";
    }

    @PostMapping("/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RulleNameDTO rulleNameDTO,
                             BindingResult result) {

        if (result.hasErrors()){
            return "redirect;/ruleName/update";
        }else {ruleNameService.updateRuleName(id,rulleNameDTO);
            return "redirect:/ruleName/list";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id) {

        ruleNameService.deleteRuleName(id);
        return "redirect:/ruleName/list";
    }
}
