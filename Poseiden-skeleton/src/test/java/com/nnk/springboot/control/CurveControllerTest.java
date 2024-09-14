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
import org.springframework.validation.*;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CurveController.class)
class CurveControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurvePointService curvePointService;

    @MockBean
    private Utils utils;



    private CurvePointDTO curvePointDTO;
    private CurvePointDTO NVcurvePointDTO;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);


        curvePointDTO = new CurvePointDTO();
        curvePointDTO.setId(1);
        curvePointDTO.setTerm(5.0);
        curvePointDTO.setValue(10.0);

        NVcurvePointDTO = new CurvePointDTO();
        NVcurvePointDTO.setTerm(5.0);
        NVcurvePointDTO.setValue(10.0);

        user = new User();
        user.setFullname("testUser");
    }

    @Test
    @WithMockUser
    void testHome() throws Exception {
        when(curvePointService.displayAllCurvePoint()).thenReturn(Collections.emptyList());
        when(utils.currentUser()).thenReturn(user);

        mockMvc.perform(get("/curvePoint/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/list"))
                .andExpect(model().attribute("curvePoints", Collections.emptyList()));
    }

    @Test
    @WithMockUser
    void testAddBidForm() throws Exception {
        mockMvc.perform(get("/curvePoint/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"));
    }

    @Test
    @WithMockUser
    void testValidate_Success() throws Exception {
        mockMvc.perform(post("/curvePoint/validate")
                        .with(csrf().asHeader())
                        .flashAttr("curvePoint", curvePointDTO))
                .andExpect(status().isFound())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/curvePoint/list"))
                .andExpect(model().hasNoErrors());

    }

    @Test
    @WithMockUser
    void testValidate_HasErrors() throws Exception {
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(true);

        mockMvc.perform(post("/curvePoint/validate")
                        .with(csrf().asHeader())
                        .flashAttr("curvePoint", NVcurvePointDTO))
                .andExpect(status().isFound());
    }

    @Test
    @WithMockUser
    void testShowUpdateForm() throws Exception {
        when(curvePointService.displayCurvePointById(1)).thenReturn(curvePointDTO);

        mockMvc.perform(get("/curvePoint/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"))
                .andExpect(model().attribute("curvePoints", curvePointDTO));


    }

    @Test
    @WithMockUser
    void testUpdateCurvePoint_Success() throws Exception {
        mockMvc.perform(post("/curvePoint/update/1")
                        .with(csrf().asHeader())
                        .flashAttr("curvePoint", curvePointDTO))
                .andExpect(status().isFound())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));

    }



    @Test
    @WithMockUser
    void testDeleteCurvePoint() throws Exception {
        doNothing().when(curvePointService).deleteCurvPoint(1);

        mockMvc.perform(get("/curvePoint/delete/1"))
                .andExpect(status().isFound())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));


    }
}
