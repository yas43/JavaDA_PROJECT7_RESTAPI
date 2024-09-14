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

import java.sql.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TradeServiceTest {

    @MockBean
    private TradeRepository tradeRepository;

    @Autowired
    private TradeService tradeService;

    private Trade trade;
    private TradeDTO tradeDTO;

    @BeforeEach
    public void setUp() {
        trade = new Trade();
        trade.setTradeId(1);
        trade.setAccount("Account1");
        trade.setType("Type1");
        trade.setBuyQuantity(100.0);
        trade.setCreationDate(new Timestamp(System.currentTimeMillis()));

        tradeDTO = new TradeDTO();
        tradeDTO.setTradeId(1);
        tradeDTO.setAccount("Account1");
        tradeDTO.setType("Type1");
        tradeDTO.setBuyQuantity(100.0);
    }

    @Test
    public void testDisplayAllTrade() {
        Trade trade1 = new Trade(1, "Account1", "Type1", 100.0, new Timestamp(System.currentTimeMillis()));
        Trade trade2 = new Trade(2, "Account2", "Type2", 200.0, new Timestamp(System.currentTimeMillis()));

        when(tradeRepository.findAll()).thenReturn(Arrays.asList(trade1, trade2));

        List<TradeDTO> result = (List<TradeDTO>) tradeService.displayAllTrade();

        assertEquals(2, result.size());
        assertEquals(trade1.getTradeId(), result.get(0).getTradeId());
        assertEquals(trade2.getTradeId(), result.get(1).getTradeId());

        verify(tradeRepository, times(1)).findAll();
    }

    @Test
    public void testAddTrade() {
        when(tradeRepository.save(any(Trade.class))).thenReturn(trade);

        Trade result = tradeService.addTrade(tradeDTO);

        assertNotNull(result);
        assertEquals(trade.getTradeId(), result.getTradeId());
        assertEquals(trade.getAccount(), result.getAccount());
        assertEquals(trade.getType(), result.getType());

        verify(tradeRepository, times(1)).save(any(Trade.class));
    }

    @Test
    public void testDisplayTradeById() {
        when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));

        TradeDTO result = tradeService.displayTradeById(1);

        assertNotNull(result);
        assertEquals(trade.getTradeId(), result.getTradeId());
        assertEquals(trade.getAccount(), result.getAccount());
        assertEquals(trade.getType(), result.getType());

        verify(tradeRepository, times(1)).findById(1);
    }

    @Test
    public void testDisplayTradeById_NotFound() {
        when(tradeRepository.findById(1)).thenReturn(Optional.empty());


        assertThrows(RuntimeException.class, () -> tradeService.displayTradeById(1));
    }

    @Test
    public void testUpdateTrade() {
        when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));
        when(tradeRepository.save(any(Trade.class))).thenReturn(trade);

        Trade updatedTrade = tradeService.updateTrade(1, tradeDTO);

        assertNotNull(updatedTrade);
        assertEquals(tradeDTO.getTradeId(), updatedTrade.getTradeId());
        assertEquals(tradeDTO.getAccount(), updatedTrade.getAccount());
        assertEquals(tradeDTO.getType(), updatedTrade.getType());

        verify(tradeRepository, times(1)).findById(1);
        verify(tradeRepository, times(1)).save(any(Trade.class));
    }

    @Test
    public void testDeleteTrade() {
        when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));

        tradeService.deleteTrade(1);

        verify(tradeRepository, times(1)).delete(trade);
    }

    @Test
    public void testDeleteTrade_NotFound() {
        when(tradeRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> tradeService.deleteTrade(1));
        assertEquals("could not find trade by this id", exception.getMessage());

        verify(tradeRepository, times(1)).findById(1);
    }
}

