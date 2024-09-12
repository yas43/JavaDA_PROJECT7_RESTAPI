package com.nnk.springboot.control;


import com.nnk.springboot.controllers.*;
import com.nnk.springboot.domain.*;
import com.nnk.springboot.domain.dto.*;
import com.nnk.springboot.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.security.test.context.support.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RuleNameController.class)
public class RuleNameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RuleNameService ruleNameService;

    @MockBean
    private Utils utils;

//    @InjectMocks
//    private RuleNameController ruleNameController;

    private RulleNameDTO rulleNameDTO;
    private RuleName NVrullName;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
//        mockMvc = MockMvcBuilders.standaloneSetup(ruleNameController).build();

        rulleNameDTO = new RulleNameDTO();
        rulleNameDTO.setId(1);
        rulleNameDTO.setName("Rule Name 1");
        rulleNameDTO.setDescription("Description 1");
        rulleNameDTO.setJson("json");
        rulleNameDTO.setTemplate("template");
        rulleNameDTO.setSqlPart("sql part");


        NVrullName = new RuleName();
        NVrullName.setId(1);
        NVrullName.setName("Rule Name 1 AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAARule Name 1 AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        NVrullName.setDescription("Description 1");
        NVrullName.setJson("json");
        NVrullName.setTemplate("template");
        NVrullName.setSqlPart("sql part");

        user = new User();
        user.setFullname("testUser");
    }

    @Test
    @WithMockUser
    void testHome() throws Exception {
//        List<RulleNameDTO> ruleNames = Arrays.asList(rulleNameDTO);
        when(ruleNameService.displayAllRuleName()).thenReturn(Collections.emptyList());
        when(utils.currentUser()).thenReturn(user);

        mockMvc.perform(get("/ruleName/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/list"))
//                .andExpect(model().attribute("ruleNames", ruleNames))
                .andExpect(model().attribute("ruleNames", Collections.emptyList()));

//        verify(ruleNameService, times(1)).displayAllRuleName();
//        verify(utils, times(1)).currentUser();
    }

    @Test
    @WithMockUser
    void testAddRuleForm() throws Exception {
        mockMvc.perform(get("/ruleName/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"));
//                .andExpect(model().attributeExists("ruleName"));
    }

    @Test
    @WithMockUser
    void testValidate_Success() throws Exception {
        mockMvc.perform(post("/ruleName/validate")
                        .with(csrf().asHeader())
                        .flashAttr("ruleName", rulleNameDTO))
                .andExpect(status().isFound())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"))
                .andExpect(model().hasNoErrors());

//        verify(ruleNameService, times(1)).addRuleName(any(RulleNameDTO.class));
    }

//    @Test
//    void testValidate_HasErrors() throws Exception {
//        BindingResult result = mock(BindingResult.class);
//        when(result.hasErrors()).thenReturn(true);
//
//        mockMvc.perform(post("/ruleName/validate")
//                        .flashAttr("ruleName", rulleNameDTO))
//                .andExpect(status().isOk())
//                .andExpect(view().name("ruleName/add"));
//
//        verify(ruleNameService, times(0)).addRuleName(any(RulleNameDTO.class));
//    }

    @Test
    @WithMockUser
    void testShowUpdateForm() throws Exception {
        when(ruleNameService.displayruleNameById(anyInt())).thenReturn(rulleNameDTO);

        mockMvc.perform(get("/ruleName/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"))
                .andExpect(model().attribute("ruleName", rulleNameDTO));

//        verify(ruleNameService, times(1)).displayruleNameById(anyInt());
    }

    @Test
    @WithMockUser
    void testUpdateRuleName_Success() throws Exception {
        mockMvc.perform(post("/ruleName/update/1")
                        .with(csrf().asHeader())
                        .flashAttr("ruleName", rulleNameDTO))
                .andExpect(status().isFound())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));

//        verify(ruleNameService, times(1)).updateRuleName(anyInt(), any(RulleNameDTO.class));
    }

//    @Test
//    @WithMockUser
//    void testUpdateRuleName_HasErrors() throws Exception {
////        BindingResult result = mock(BindingResult.class);
////        when(result.hasErrors()).thenReturn(true);
//        when(ruleNameService.updateRuleName(anyInt(),any(RulleNameDTO.class))).thenReturn(NVrullName);
//
//        mockMvc.perform(post("/ruleName/update/1")
//                        .with(csrf().asHeader())
//                        .flashAttr("ruleName",NVrullName ))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/ruleName/update"));
//
////        verify(ruleNameService, times(0)).updateRuleName(anyInt(), any(RulleNameDTO.class));
//    }

    @Test
    @WithMockUser
    void testDeleteRuleName() throws Exception {
        doNothing().when(ruleNameService).deleteRuleName(anyInt());

        mockMvc.perform(get("/ruleName/delete/1"))
                .andExpect(status().isFound())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));

//        verify(ruleNameService, times(1)).deleteRuleName(anyInt());
    }
}

