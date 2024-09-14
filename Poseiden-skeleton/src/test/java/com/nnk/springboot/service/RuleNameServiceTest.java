package com.nnk.springboot.service;

import com.nnk.springboot.domain.*;
import com.nnk.springboot.domain.dto.*;
import com.nnk.springboot.repositories.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.junit.jupiter.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.boot.test.mock.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class RuleNameServiceTest {

    @MockBean
    private RuleNameRepository ruleNameRepository;

    @Autowired
    private RuleNameService ruleNameService;

    private RuleName ruleName;
    private RuleNameDTO ruleNameDTO;

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

        ruleNameDTO = new RuleNameDTO();
        ruleNameDTO.setId(1);
        ruleNameDTO.setName("Test Name");
        ruleNameDTO.setDescription("Test Description");
        ruleNameDTO.setJson("Test Json");
        ruleNameDTO.setSqlStr("Test SQL String");
        ruleNameDTO.setTemplate("Test Template");
        ruleNameDTO.setSqlPart("Test SQL Part");
    }

    @Test
    public void testDisplayAllRuleName() {
        RuleName ruleName1 = new RuleName(1, "Rule 1", "Desc 1", "Json 1", "Template 1", "SQL 1", "SQLPart 1");
        RuleName ruleName2 = new RuleName(2, "Rule 2", "Desc 2", "Json 2", "Template 2", "SQL 2", "SQLPart 2");

        when(ruleNameRepository.findAll()).thenReturn(Arrays.asList(ruleName1, ruleName2));

        List<RuleNameDTO> result = ruleNameService.displayAllRuleName();

        assertEquals(2, result.size());
        assertEquals(ruleName1.getId(), result.get(0).getId());
        assertEquals(ruleName2.getId(), result.get(1).getId());

        verify(ruleNameRepository, times(1)).findAll();
    }

    @Test
    public void testAddRuleName() {
        when(ruleNameRepository.save(any(RuleName.class))).thenReturn(ruleName);

        RuleName result = ruleNameService.addRuleName(ruleNameDTO);

        assertNotNull(result);
        assertEquals(ruleName.getId(), result.getId());
        assertEquals(ruleName.getName(), result.getName());
        assertEquals(ruleName.getDescription(), result.getDescription());

        verify(ruleNameRepository, times(1)).save(any(RuleName.class));
    }

    @Test
    public void testDisplayRuleNameById() {
        when(ruleNameRepository.findById(1)).thenReturn(Optional.of(ruleName));

        RuleNameDTO result = ruleNameService.displayruleNameById(1);

        assertEquals(ruleName.getId(), result.getId());
        assertEquals(ruleName.getName(), result.getName());
        assertEquals(ruleName.getDescription(), result.getDescription());

        verify(ruleNameRepository, times(1)).findById(1);
    }

    @Test
    public void testDisplayRuleNameById_NotFound() {
        when(ruleNameRepository.findById(1)).thenReturn(Optional.empty());


        assertThrows(RuntimeException.class,()->ruleNameService.displayruleNameById(1));
    }

    @Test
    public void testUpdateRuleName() {
        when(ruleNameRepository.findById(1)).thenReturn(Optional.of(ruleName));
        when(ruleNameRepository.save(any(RuleName.class))).thenReturn(ruleName);

        RuleName updatedRuleName = ruleNameService.updateRuleName(1, ruleNameDTO);

        assertNotNull(updatedRuleName);
        assertEquals(ruleNameDTO.getId(), updatedRuleName.getId());
        assertEquals(ruleNameDTO.getName(), updatedRuleName.getName());
        assertEquals(ruleNameDTO.getDescription(), updatedRuleName.getDescription());

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
