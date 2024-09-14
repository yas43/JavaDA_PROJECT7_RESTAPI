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

@WebMvcTest(BidListController.class)

class BidListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BidListService bidListService;

    @MockBean
    private Utils utils;



    private BidListDTO bidListDTO;
    private BidListDTO NVbidListDTO;

    private BidList bidList;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bidListDTO = new BidListDTO();
        bidListDTO.setId(1);
        bidListDTO.setAccount("Account");
        bidListDTO.setType("Type");
        bidListDTO.setBidQuantity(100.0);

        NVbidListDTO = new BidListDTO();
        NVbidListDTO.setId(1);
        NVbidListDTO.setAccount("Account");
        NVbidListDTO.setType("");
        NVbidListDTO.setBidQuantity(100.0);
//
        bidList = new BidList();
        bidList.setBidListId(1);
        bidList.setAccount("Account");
        bidList.setType("Type");
        bidList.setBidQuantity(100.0);

        user = new User();
        user.setFullname("testUser");

    }

    @Test
    @WithMockUser(username = "User", roles = "admin")
    void testHome() throws Exception {
        when(bidListService.displayAllBidList()).thenReturn(Collections.emptyList());
        when(utils.currentUser()).thenReturn(user);

        mockMvc.perform(get("/bidList/list"))

                .andExpect(status().isOk())
                .andExpect(view().name("bidList/list"))
                .andExpect(model().attribute("bidLists",  Collections.emptyList()));


    }

    @Test
    @WithMockUser
    void testAddBidForm() throws Exception {
        mockMvc.perform(get("/bidList/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"));

    }

    @Test
    @WithMockUser
    void testValidateBidForm_Success() throws Exception {
        mockMvc.perform(post("/bidList/validate")
                        .with(csrf().asHeader())
                        .flashAttr("bidList", bidListDTO))

                .andExpect(status().isFound())

                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/bidList/list"))
                .andExpect(redirectedUrl("/bidList/list"));

    }

    @Test
    @WithMockUser
    void testValidateBidForm_HasErrors() throws Exception {

        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(true);

        mockMvc.perform(post("/bidList/validate")
                        .with(csrf().asHeader())
                        .flashAttr("bidList", NVbidListDTO))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"));

    }

    @Test
    @WithMockUser
    void testShowUpdateForm_ValidId() throws Exception {
        when(bidListService.isExist(1)).thenReturn(true);
        when(bidListService.displayBidListById(1)).thenReturn(bidListDTO);

        mockMvc.perform(get("/bidList/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"))
                .andExpect(model().attributeExists("bidListDTO"));

    }

    @Test
    @WithMockUser
    void testShowUpdateForm_InvalidId() throws Exception {
        when(bidListService.isExist(1)).thenReturn(false);

        mockMvc.perform(get("/bidList/update/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/update"));

    }



    @Test
    @WithMockUser
    void testDeleteBid() throws Exception {
        doNothing().when(bidListService).deleteBidList(1);
        when(bidListService.displayAllBidList()).thenReturn(Arrays.asList(bidListDTO));

        mockMvc.perform(get("/bidList/delete/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/list"))
                .andExpect(model().attribute("bidLists", Arrays.asList(bidListDTO)));

    }
}
