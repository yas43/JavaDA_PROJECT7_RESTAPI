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

@WebMvcTest(TradeController.class)
public class TradeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TradeService tradeService;

    @MockBean
    private Utils utils;



    private TradeDTO tradeDTO;
    private TradeDTO NVtradeDTO;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);


        tradeDTO = new TradeDTO();
        tradeDTO.setTradeId(1);
        tradeDTO.setAccount("Account1");
        tradeDTO.setType("Type1");
        tradeDTO.setBuyQuantity(100.0);

        NVtradeDTO = new TradeDTO();
        NVtradeDTO.setTradeId(1);
        NVtradeDTO.setAccount("Account1nvAccount1nvAccount1nvAccount1nv");
        NVtradeDTO.setType("");
        NVtradeDTO.setBuyQuantity(100.0);

        user = new User();
        user.setFullname("testUser");
    }

    @Test
    @WithMockUser
    void testHome() throws Exception {

        when(tradeService.displayAllTrade()).thenReturn(Collections.emptyList());
        when(utils.currentUser()).thenReturn(user);

        mockMvc.perform(get("/trade/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/list"))
                .andExpect(model().attribute("trades", Collections.emptyList()));



    }

    @Test
    @WithMockUser
    void testAddTradeForm() throws Exception {
        mockMvc.perform(get("/trade/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"));

    }

    @Test
    @WithMockUser
    void testValidate_Success() throws Exception {
        mockMvc.perform(post("/trade/validate")
                        .with(csrf().asHeader())
                        .flashAttr("trade", tradeDTO))
                .andExpect(status().isFound())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/trade/list"))
                .andExpect(model().hasNoErrors());

    }

    @Test
    @WithMockUser
    void testValidate_HasErrors() throws Exception {
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(true);

        mockMvc.perform(post("/trade/validate")
                        .with(csrf().asHeader())
                        .flashAttr("trade", NVtradeDTO))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"));

        verify(tradeService, times(0)).addTrade(any(TradeDTO.class));
    }

    @Test
    @WithMockUser
    void testShowUpdateForm() throws Exception {
        when(tradeService.displayTradeById(anyInt())).thenReturn(tradeDTO);

        mockMvc.perform(get("/trade/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"))
                .andExpect(model().attribute("tradeDTO", tradeDTO));

        verify(tradeService, times(1)).displayTradeById(anyInt());
    }

    @Test
    @WithMockUser
    void testUpdateTrade_Success() throws Exception {
        mockMvc.perform(post("/trade/update/1")
                        .with(csrf().asHeader())
                        .flashAttr("tradeDTO", tradeDTO))
                .andExpect(status().isFound())
                .andExpect(status().is3xxRedirection());

    }

    @Test
    @WithMockUser
    void testUpdateTrade_HasErrors() throws Exception {
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(true);

        mockMvc.perform(post("/trade/update/1")
                        .with(csrf().asHeader())
                        .flashAttr("tradeDTO", tradeDTO))
                .andExpect(status().isFound());

    }

    @Test
    @WithMockUser
    void testDeleteTrade() throws Exception {
        doNothing().when(tradeService).deleteTrade(anyInt());

        mockMvc.perform(get("/trade/delete/1"))
                .andExpect(status().isFound())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));


    }
}

