package com.nnk.springboot.services;

import com.nnk.springboot.domain.*;
import com.nnk.springboot.domain.dto.*;
import com.nnk.springboot.repositories.*;
import com.nnk.springboot.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class RuleNameServiceTest {

    @MockBean
    private RuleNameRepository ruleNameRepository;

    @Autowired
    private RuleNameService ruleNameService;

    private RuleName ruleName;
    private RulleNameDTO rulleNameDTO;

    @BeforeEach
    public void setUp() {
        ruleName = new RuleName();
        ruleName.setId(1);
        ruleName.setName("Test Name");
        ruleName.setDescription("Test Description");
        ruleName.setJson("Test Json");
        ruleName.setSqlStr("Test SQL String");
        ruleName.setTemplate("Test Template");
        ruleName.setSqlPart("Test SQL Part");

        rulleNameDTO = new RulleNameDTO();
        rulleNameDTO.setId(1);
        rulleNameDTO.setName("Test Name");
        rulleNameDTO.setDescription("Test Description");
        rulleNameDTO.setJson("Test Json");
        rulleNameDTO.setSqlStr("Test SQL String");
        rulleNameDTO.setTemplate("Test Template");
        rulleNameDTO.setSqlPart("Test SQL Part");
    }

    @Test
    public void testDisplayAllRuleName() {
        RuleName ruleName1 = new RuleName(1, "Rule 1", "Desc 1", "Json 1", "Template 1", "SQL 1", "SQLPart 1");
        RuleName ruleName2 = new RuleName(2, "Rule 2", "Desc 2", "Json 2", "Template 2", "SQL 2", "SQLPart 2");

        when(ruleNameRepository.findAll()).thenReturn(Arrays.asList(ruleName1, ruleName2));

        List<RulleNameDTO> result = ruleNameService.displayAllRuleName();

        assertEquals(2, result.size());
        assertEquals(ruleName1.getId(), result.get(0).getId());
        assertEquals(ruleName2.getId(), result.get(1).getId());

        verify(ruleNameRepository, times(1)).findAll();
    }

    @Test
    public void testAddRuleName() {
        when(ruleNameRepository.save(any(RuleName.class))).thenReturn(ruleName);

        RuleName result = ruleNameService.addRuleName(rulleNameDTO);

        assertNotNull(result);
        assertEquals(ruleName.getId(), result.getId());
        assertEquals(ruleName.getName(), result.getName());
        assertEquals(ruleName.getDescription(), result.getDescription());

        verify(ruleNameRepository, times(1)).save(any(RuleName.class));
    }

    @Test
    public void testDisplayRuleNameById() {
        when(ruleNameRepository.findById(1)).thenReturn(Optional.of(ruleName));

        RulleNameDTO result = ruleNameService.displayruleNameById(1);

        assertEquals(ruleName.getId(), result.getId());
        assertEquals(ruleName.getName(), result.getName());
        assertEquals(ruleName.getDescription(), result.getDescription());

        verify(ruleNameRepository, times(1)).findById(1);
    }

    @Test
    public void testDisplayRuleNameById_NotFound() {
        when(ruleNameRepository.findById(1)).thenReturn(Optional.empty());

//        RuntimeException exception = assertThrows(RuntimeException.class, () -> ruleNameService.displayruleNameById(1));
//        assertEquals("could not find rule name by this id", exception.getMessage());

//        verify(ruleNameRepository, times(1)).findById(1);
        assertThrows(RuntimeException.class,()->ruleNameService.displayruleNameById(1));
    }

    @Test
    public void testUpdateRuleName() {
        when(ruleNameRepository.findById(1)).thenReturn(Optional.of(ruleName));
        when(ruleNameRepository.save(any(RuleName.class))).thenReturn(ruleName);

        RuleName updatedRuleName = ruleNameService.updateRuleName(1, rulleNameDTO);

        assertNotNull(updatedRuleName);
        assertEquals(rulleNameDTO.getId(), updatedRuleName.getId());
        assertEquals(rulleNameDTO.getName(), updatedRuleName.getName());
        assertEquals(rulleNameDTO.getDescription(), updatedRuleName.getDescription());

        verify(ruleNameRepository, times(1)).findById(1);
        verify(ruleNameRepository, times(1)).save(any(RuleName.class));
    }

    @Test
    public void testDeleteRuleName() {
        when(ruleNameRepository.findById(1)).thenReturn(Optional.of(ruleName));

        ruleNameService.deleteRuleName(1);

        verify(ruleNameRepository, times(1)).delete(ruleName);
    }

    @Test
    public void testDeleteRuleName_NotFound() {
        when(ruleNameRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> ruleNameService.deleteRuleName(1));
        assertEquals("could not find rule name by this id", exception.getMessage());

        verify(ruleNameRepository, times(1)).findById(1);
    }
}
