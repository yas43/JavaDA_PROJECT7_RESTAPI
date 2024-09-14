package com.nnk.springboot.control;


import com.nnk.springboot.controllers.*;
import com.nnk.springboot.domain.*;
import com.nnk.springboot.domain.dto.*;
import com.nnk.springboot.service.*;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.security.test.context.support.*;
import org.springframework.test.web.servlet.*;

import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
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



    private RuleNameDTO ruleNameDTO;
    private RuleName NVrullName;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);


        ruleNameDTO = new RuleNameDTO();
        ruleNameDTO.setId(1);
        ruleNameDTO.setName("Rule Name 1");
        ruleNameDTO.setDescription("Description 1");
        ruleNameDTO.setJson("json");
        ruleNameDTO.setTemplate("template");
        ruleNameDTO.setSqlPart("sql part");


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

        when(ruleNameService.displayAllRuleName()).thenReturn(Collections.emptyList());
        when(utils.currentUser()).thenReturn(user);

        mockMvc.perform(get("/ruleName/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/list"))
                .andExpect(model().attribute("ruleNames", Collections.emptyList()));


    }

    @Test
    @WithMockUser
    void testAddRuleForm() throws Exception {
        mockMvc.perform(get("/ruleName/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"));

    }

    @Test
    @WithMockUser
    void testValidate_Success() throws Exception {
        mockMvc.perform(post("/ruleName/validate")
                        .with(csrf().asHeader())
                        .flashAttr("ruleName", ruleNameDTO))
                .andExpect(status().isFound())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"))
                .andExpect(model().hasNoErrors());


    }


    @Test
    @WithMockUser
    void testShowUpdateForm() throws Exception {
        when(ruleNameService.displayruleNameById(anyInt())).thenReturn(ruleNameDTO);

        mockMvc.perform(get("/ruleName/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"))
                .andExpect(model().attribute("ruleNameDTO", ruleNameDTO));


    }

    @Test
    @WithMockUser
    void testUpdateRuleName_Success() throws Exception {
        mockMvc.perform(post("/ruleName/update/1")
                        .with(csrf().asHeader())
                        .flashAttr("ruleName", ruleNameDTO))
                .andExpect(status().isFound())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));


    }



    @Test
    @WithMockUser
    void testDeleteRuleName() throws Exception {
        doNothing().when(ruleNameService).deleteRuleName(anyInt());

        mockMvc.perform(get("/ruleName/delete/1"))
                .andExpect(status().isOk());



    }
}

