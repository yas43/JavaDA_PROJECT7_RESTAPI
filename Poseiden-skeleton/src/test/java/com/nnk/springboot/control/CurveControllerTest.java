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
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
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

//    @InjectMocks
//    private CurveController curveController;

    private CurvePointDTO curvePointDTO;
    private CurvePointDTO NVcurvePointDTO;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
//        mockMvc = MockMvcBuilders.standaloneSetup(curveController).build();

        curvePointDTO = new CurvePointDTO();
        curvePointDTO.setId(1);
        curvePointDTO.setTerm(5.0);
        curvePointDTO.setValue(10.0);

        NVcurvePointDTO = new CurvePointDTO();
//        NVcurvePointDTO.setId(null);
        NVcurvePointDTO.setTerm(5.0);
        NVcurvePointDTO.setValue(10.0);

        user = new User();
        user.setFullname("testUser");
    }

    @Test
    @WithMockUser
    void testHome() throws Exception {
//        List<CurvePointDTO> curvePoints = Arrays.asList(curvePointDTO);
        when(curvePointService.displayAllCurvePoint()).thenReturn(Collections.emptyList());
        when(utils.currentUser()).thenReturn(user);

        mockMvc.perform(get("/curvePoint/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/list"))
//                .andExpect(model().attribute("curvePoints", curvePoints))
                .andExpect(model().attribute("curvePoints", Collections.emptyList()));

//        verify(curvePointService, times(1)).displayAllCurvePoint();
//        verify(utils, times(1)).currentUser();
    }
//
    @Test
    @WithMockUser
    void testAddBidForm() throws Exception {
        mockMvc.perform(get("/curvePoint/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"));
//                .andExpect(model().attributeExists("curvePoint"));
    }
//
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
//                .andExpect(redirectedUrl("/curvePoint/list"));

//        verify(curvePointService, times(1)).addCurvePoint(any(CurvePointDTO.class));
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
//                .andExpect(view().name("curvePoint/add"));

//        verify(curvePointService, times(0)).addCurvePoint(any(CurvePointDTO.class));
    }

    @Test
    @WithMockUser
    void testShowUpdateForm() throws Exception {
        when(curvePointService.displayCurvePointById(1)).thenReturn(curvePointDTO);

        mockMvc.perform(get("/curvePoint/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"))
                .andExpect(model().attribute("curvePoints", curvePointDTO));

//        verify(curvePointService, times(1)).displayCurvePointById(1);
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

//        verify(curvePointService, times(1)).updateCurvePoint(1, curvePointDTO);
    }

//    @Test
//    @WithMockUser
//    void testUpdateCurvePoint_HasErrors() throws Exception {
//        BindingResult result = mock(BindingResult.class);
//        when(result.hasErrors()).thenReturn(true);
//
//        mockMvc.perform(post("/update/1")
//                        .flashAttr("curvePoint", curvePointDTO))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/curvePoint/update"));
//
//        verify(curvePointService, times(0)).updateCurvePoint(anyInt(), any(CurvePointDTO.class));
//    }

    @Test
    @WithMockUser
    void testDeleteCurvePoint() throws Exception {
        doNothing().when(curvePointService).deleteCurvPoint(1);

        mockMvc.perform(get("/curvePoint/delete/1"))
                .andExpect(status().isFound())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));

//        verify(curvePointService, times(1)).deleteCurvPoint(1);
    }
}
