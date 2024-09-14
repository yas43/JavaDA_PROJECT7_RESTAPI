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
//@TestPropertySource(locations = "classpath:application-prod.properties")
public class BidListServiceTest {

    @MockBean
    private BidListRepository bidListRepository;

    @Autowired
    private BidListService bidListService;

    private BidList bidList;
    private BidListDTO bidListDTO;

    @BeforeEach
    public void setUp() {
        bidList = new BidList();
        bidList.setBidListId(1);
        bidList.setAccount("TestAccount");
        bidList.setType("TestType");
        bidList.setBidQuantity(100.0);

        bidListDTO = new BidListDTO();
        bidListDTO.setId(1);
        bidListDTO.setAccount("TestAccount");
        bidListDTO.setType("TestType");
        bidListDTO.setBidQuantity(100.0);
    }

    @Test
    public void testDisplayBidListById() {
        when(bidListRepository.findByBidListId(1)).thenReturn(Optional.of(bidList));

        BidListDTO result = bidListService.displayBidListById(1);

        assertEquals(bidList.getBidListId(), result.getId());
        assertEquals(bidList.getAccount(), result.getAccount());
        assertEquals(bidList.getType(), result.getType());
        assertNotNull(result);


        verify(bidListRepository, times(1)).findByBidListId(1);
    }

    @Test
    public void testDisplayBidListById_NotFound() {
        when(bidListRepository.findByBidListId(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> bidListService.displayBidListById(1));
    }

    @Test
    public void testDisplayAllBidList() {
        BidList bidList1 = new BidList(2, "Account1", "Type1", 200.0, new Timestamp(System.currentTimeMillis()));
        BidList bidList2 = new BidList(3, "Account2", "Type2", 300.0, new Timestamp(System.currentTimeMillis()));

        when(bidListRepository.findAll()).thenReturn(Arrays.asList(bidList1, bidList2));

        List<BidListDTO> result = bidListService.displayAllBidList();

        assertEquals(2, result.size());
        assertEquals(bidList1.getBidListId(), result.get(0).getId());
        assertEquals(bidList2.getBidListId(), result.get(1).getId());

        verify(bidListRepository, times(1)).findAll();
    }

    @Test
    public void testAddBidList() {
        when(bidListRepository.save(any(BidList.class))).thenReturn(bidList);

        BidList result = bidListService.addBidList(bidListDTO);

        assertNotNull(result);
        assertEquals(bidList.getBidListId(), result.getBidListId());

        verify(bidListRepository, times(1)).save(any(BidList.class));
    }

    @Test
    public void testDeleteBidList() {
        when(bidListRepository.findByBidListId(1)).thenReturn(Optional.of(bidList));

        bidListService.deleteBidList(1);

        verify(bidListRepository, times(1)).delete(bidList);
    }

    @Test
    public void testDeleteBidList_NotFound() {
        when(bidListRepository.findByBidListId(1)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> bidListService.deleteBidList(1));
        assertEquals("can't find this id", exception.getMessage());

        verify(bidListRepository, times(1)).findByBidListId(1);
    }

    @Test
    public void testUpdateBidList() {
        when(bidListRepository.findByBidListId(1)).thenReturn(Optional.of(bidList));
        when(bidListRepository.save(any(BidList.class))).thenReturn(bidList);

        BidList updatedBidList = bidListService.updateBidList(1, bidListDTO);

        assertNotNull(updatedBidList);
        assertEquals(bidListDTO.getAccount(), updatedBidList.getAccount());
        assertEquals(bidListDTO.getType(), updatedBidList.getType());
        assertEquals(bidListDTO.getBidQuantity(), updatedBidList.getBidQuantity());

        verify(bidListRepository, times(1)).findByBidListId(1);
        verify(bidListRepository, times(1)).save(any(BidList.class));
    }

    @Test
    public void testIsExist() {
        when(bidListRepository.existsById(1)).thenReturn(true);

        assertTrue(bidListService.isExist(1));

        verify(bidListRepository, times(1)).existsById(1);
    }
}

