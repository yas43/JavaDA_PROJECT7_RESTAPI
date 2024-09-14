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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RatingController.class)
public class RatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RatingService ratingService;

    @MockBean
    private Utils utils;



    private RatingDTO ratingDTO;
    private RatingDTO NVratingDTO;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);


        ratingDTO = new RatingDTO();
        ratingDTO.setId(1);
        ratingDTO.setMoodysRating("Aa1");
        ratingDTO.setSandPRating("AA+");
        ratingDTO.setFitchRating("AA");
        ratingDTO.setOrder(1);

        NVratingDTO = new RatingDTO();
        NVratingDTO.setId(1);
        NVratingDTO.setMoodysRating("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        NVratingDTO.setSandPRating("AA+");
        NVratingDTO.setFitchRating("AA");
        NVratingDTO.setOrder(1);

        user = new User();
        user.setFullname("testUser");
    }

    @Test
    @WithMockUser
    void testHome() throws Exception {
//        List<RatingDTO> ratings = Arrays.asList(ratingDTO);
        when(ratingService.displayAllRating()).thenReturn(Collections.emptyList());
        when(utils.currentUser()).thenReturn(user);

        mockMvc.perform(get("/rating/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/list"))
//                .andExpect(model().attribute("ratings", ratings))
                .andExpect(model().attribute("ratings", Collections.emptyList()));

        verify(ratingService, times(1)).displayAllRating();
        verify(utils, times(1)).currentUser();
    }

    @Test
    @WithMockUser
    void testAddRatingForm() throws Exception {
        mockMvc.perform(get("/rating/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"));

    }

    @Test
    @WithMockUser
    void testValidate_Success() throws Exception {
        mockMvc.perform(post("/rating/validate")
                        .with(csrf().asHeader())
                        .flashAttr("rating", ratingDTO))
                .andExpect(status().isFound())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/rating/list"))
                .andExpect(model().hasNoErrors());


        verify(ratingService, times(1)).addRating(any(RatingDTO.class));
    }

    @Test
    @WithMockUser
    void testValidate_HasErrors() throws Exception {
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(true);

        mockMvc.perform(post("/rating/validate")
                        .with(csrf().asHeader())
                        .flashAttr("rating", NVratingDTO))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"));

        verify(ratingService, times(0)).addRating(any(RatingDTO.class));
    }

    @Test
    @WithMockUser
    void testShowUpdateForm() throws Exception {
        when(ratingService.displayRatingById(anyInt())).thenReturn(ratingDTO);

        mockMvc.perform(get("/rating/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"))
                .andExpect(model().attribute("rating", ratingDTO));

        verify(ratingService, times(1)).displayRatingById(anyInt());
    }

    @Test
    @WithMockUser
    void testUpdateRating_Success() throws Exception {
        mockMvc.perform(post("/rating/update/1")
                        .with(csrf().asHeader())
                        .flashAttr("rating", ratingDTO))
                .andExpect(status().isFound())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));


    }

    @Test
    @WithMockUser
    void testUpdateRating_HasErrors() throws Exception {
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(true);

        mockMvc.perform(post("/rating/update/1")
                        .with(csrf().asHeader())
                        .flashAttr("rating",NVratingDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));


    }

    @Test
    @WithMockUser
    void testDeleteRating() throws Exception {
        doNothing().when(ratingService).deleteRating(anyInt());

        mockMvc.perform(get("/rating/delete/1"))
                .andExpect(status().isFound())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));

    }
}

