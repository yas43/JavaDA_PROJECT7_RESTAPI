package com.nnk.springboot.service;

import com.nnk.springboot.domain.*;
import com.nnk.springboot.domain.dto.*;
import com.nnk.springboot.repositories.*;
import org.springframework.stereotype.*;

import java.util.*;
import java.util.stream.*;

import static java.util.stream.Collectors.toList;

@Service
public class RuleNameService {
    private final RuleNameRepository ruleNameRepository;

    public RuleNameService(RuleNameRepository ruleNameRepository) {
        this.ruleNameRepository = ruleNameRepository;
    }

    /**
     * Find list ruleName and Convert ruleNameDto
     * @return the list of BidListDto
     */
    public List<RulleNameDTO> displayAllRuleName() {
        return ruleNameRepository.findAll()
                .stream()
                .map(
                        ruleName -> {
                            return new RulleNameDTO(ruleName.getId(),
                                    ruleName.getName(),
                                    ruleName.getDescription(),
                                    ruleName.getJson(),
                                    ruleName.getSqlStr(),
                                    ruleName.getTemplate(),
                                    ruleName.getSqlPart());

                        })
                .collect(toList());

    }

    /**
     * adding new ruleName to database , convert ruleNameDTO to bisList
     * @param rulleNameDTO given ruleName by user
     * @return accepted bidList save in DB
     */
    public RuleName addRuleName(RulleNameDTO rulleNameDTO) {
        RuleName ruleName = new RuleName();
        ruleName.setName(rulleNameDTO.getName());
        ruleName.setDescription(rulleNameDTO.getDescription());
        ruleName.setJson(rulleNameDTO.getJson());
        ruleName.setTemplate(rulleNameDTO.getTemplate());
        ruleName.setSqlPart(rulleNameDTO.getSqlPart());
        ruleName.setSqlStr(rulleNameDTO.getSqlStr());
        return ruleNameRepository.save(ruleName);
    }

    /**
     * find ruleName by via id , convert to ruleNameDTO
     * @param id id
     * @return corresponding ruleName  or issue ruleNameNotFoundException
     */
    public RulleNameDTO displayruleNameById(Integer id) {
        RuleName ruleName = ruleNameRepository.findById(id)
                .orElseThrow(()->new RuntimeException("could not find rule name by this id"));
        RulleNameDTO rulleNameDTO = new RulleNameDTO();
        rulleNameDTO.setId(ruleName.getId());
        rulleNameDTO.setName(ruleName.getName());
        rulleNameDTO.setDescription(ruleName.getDescription());
        rulleNameDTO.setTemplate(ruleName.getTemplate());
        rulleNameDTO.setJson(ruleName.getJson());
        rulleNameDTO.setSqlPart(ruleName.getSqlPart());
        rulleNameDTO.setSqlStr(ruleName.getSqlStr());
        return rulleNameDTO;
    }

    /**
     * Updates an existing ruleName object with the provided new values.
     * @param id the ID of the ruleName object to update.
     * @param rulleNameDTO  the ruleName object containing the new values.
     * @return the updated ruleName object saved to the database.
     */
    public RuleName updateRuleName(Integer id, RulleNameDTO rulleNameDTO) {
        RuleName ruleName = ruleNameRepository.findById(id)
                .orElseThrow(()->new RuntimeException("could not find rule name by given id"));
        ruleName.setId(rulleNameDTO.getId());
        ruleName.setName(rulleNameDTO.getName());
        ruleName.setDescription(rulleNameDTO.getDescription());
        ruleName.setJson(rulleNameDTO.getJson());
        ruleName.setTemplate(rulleNameDTO.getTemplate());
        ruleName.setSqlStr(rulleNameDTO.getSqlStr());
        ruleName.setSqlPart(rulleNameDTO.getSqlPart());
        return ruleNameRepository.save(ruleName);
    }

    /**
     * Deletes a ruleName object by its ID.
     * @param id the ID of the ruleName object to delete.
     */
    public void deleteRuleName(Integer id) {
        RuleName ruleName = ruleNameRepository.findById(id)
                .orElseThrow(()->new RuntimeException("could not find rule name by this id"));
        ruleNameRepository.delete(ruleName);
    }
}
