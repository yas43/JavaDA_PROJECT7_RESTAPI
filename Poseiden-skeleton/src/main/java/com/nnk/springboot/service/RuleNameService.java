package com.nnk.springboot.service;

import com.nnk.springboot.domain.*;
import com.nnk.springboot.domain.dto.*;
import com.nnk.springboot.repositories.*;
import org.springframework.stereotype.*;

import java.util.*;

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
    public List<RuleNameDTO> displayAllRuleName() {
        return ruleNameRepository.findAll()
                .stream()
                .map(
                        ruleName -> {
                            return new RuleNameDTO(ruleName.getId(),
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
     * @param ruleNameDTO given ruleName by user
     * @return accepted bidList save in DB
     */
    public RuleName addRuleName(RuleNameDTO ruleNameDTO) {
        RuleName ruleName = new RuleName();
        ruleName.setName(ruleNameDTO.getName());
        ruleName.setDescription(ruleNameDTO.getDescription());
        ruleName.setJson(ruleNameDTO.getJson());
        ruleName.setTemplate(ruleNameDTO.getTemplate());
        ruleName.setSqlPart(ruleNameDTO.getSqlPart());
        ruleName.setSqlStr(ruleNameDTO.getSqlStr());
        return ruleNameRepository.save(ruleName);
    }

    /**
     * find ruleName by via id , convert to ruleNameDTO
     * @param id id
     * @return corresponding ruleName  or issue ruleNameNotFoundException
     */
    public RuleNameDTO displayruleNameById(Integer id) {
        RuleName ruleName = ruleNameRepository.findById(id)
                .orElseThrow(()->new RuntimeException("could not find rule name by this id"));
        RuleNameDTO ruleNameDTO = new RuleNameDTO();
        ruleNameDTO.setId(ruleName.getId());
        ruleNameDTO.setName(ruleName.getName());
        ruleNameDTO.setDescription(ruleName.getDescription());
        ruleNameDTO.setTemplate(ruleName.getTemplate());
        ruleNameDTO.setJson(ruleName.getJson());
        ruleNameDTO.setSqlPart(ruleName.getSqlPart());
        ruleNameDTO.setSqlStr(ruleName.getSqlStr());
        return ruleNameDTO;
    }

    /**
     * Updates an existing ruleName object with the provided new values.
     * @param id the ID of the ruleName object to update.
     * @param ruleNameDTO  the ruleName object containing the new values.
     * @return the updated ruleName object saved to the database.
     */
    public RuleName updateRuleName(Integer id, RuleNameDTO ruleNameDTO) {
        RuleName ruleName = ruleNameRepository.findById(id)
                .orElseThrow(()->new RuntimeException("could not find rule name by given id"));
        ruleName.setId(ruleNameDTO.getId());
        ruleName.setName(ruleNameDTO.getName());
        ruleName.setDescription(ruleNameDTO.getDescription());
        ruleName.setJson(ruleNameDTO.getJson());
        ruleName.setTemplate(ruleNameDTO.getTemplate());
        ruleName.setSqlStr(ruleNameDTO.getSqlStr());
        ruleName.setSqlPart(ruleNameDTO.getSqlPart());
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
