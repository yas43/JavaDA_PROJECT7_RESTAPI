package com.nnk.springboot.service;

import com.nnk.springboot.domain.*;
import com.nnk.springboot.domain.dto.*;
import com.nnk.springboot.repositories.*;
import com.nnk.springboot.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.test.context.junit4.*;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CurvePointServiceTest {

    @MockBean
    private CurvePointRepository curvePointRepository;

   @Autowired
    private CurvePointService curvePointService;

    private CurvePoint curvePoint;
    private CurvePointDTO curvePointDTO;

    @BeforeEach
    public void setUp() {
        curvePoint = new CurvePoint();
        curvePoint.setId(1);
        curvePoint.setTerm(10.0);
        curvePoint.setValue(100.0);

        curvePointDTO = new CurvePointDTO();
        curvePointDTO.setId(1);
        curvePointDTO.setTerm(10.0);
        curvePointDTO.setValue(100.0);
    }

    @Test
    public void testDisplayAllCurvePoint() {
        CurvePoint curvePoint1 = new CurvePoint(1, 10.0, 100.0, new Timestamp(System.currentTimeMillis()));
        CurvePoint curvePoint2 = new CurvePoint(2, 20.0, 200.0, new Timestamp(System.currentTimeMillis()));

        when(curvePointRepository.findAll()).thenReturn(Arrays.asList(curvePoint1, curvePoint2));

        List<CurvePointDTO> result = curvePointService.displayAllCurvePoint();

        assertEquals(2, result.size());
        assertEquals(curvePoint1.getId(), result.get(0).getId());
        assertEquals(curvePoint2.getId(), result.get(1).getId());

        verify(curvePointRepository, times(1)).findAll();
    }

    @Test
    public void testAddCurvePoint() {
        when(curvePointRepository.save(any(CurvePoint.class))).thenReturn(curvePoint);

        CurvePoint result = curvePointService.addCurvePoint(curvePointDTO);

        assertNotNull(result);
        assertEquals(curvePoint.getId(), result.getId());
        assertEquals(curvePoint.getTerm(), result.getTerm());
        assertEquals(curvePoint.getValue(), result.getValue());

        verify(curvePointRepository, times(1)).save(any(CurvePoint.class));
    }

    @Test
    public void testDisplayCurvePointById() {
        when(curvePointRepository.findById(1)).thenReturn(Optional.of(curvePoint));

        CurvePointDTO result = curvePointService.displayCurvePointById(1);

        assertEquals(curvePoint.getId(), result.getId());
        assertEquals(curvePoint.getTerm(), result.getTerm());
        assertEquals(curvePoint.getValue(), result.getValue());

        verify(curvePointRepository, times(1)).findById(1);
    }

    @Test
    public void testDisplayCurvePointById_NotFound() {
        when(curvePointRepository.findById(1)).thenReturn(Optional.empty());

//        RuntimeException exception = assertThrows(RuntimeException.class, () -> curvePointService.displayCurvePointById(1));
//        assertEquals("could not find a curve point by this id", exception.getMessage());
//
//        verify(curvePointRepository, times(1)).findById(1);

        assertThrows(RuntimeException.class, () -> curvePointService.displayCurvePointById(1));
    }

    @Test
    public void testUpdateCurvePoint() {
        when(curvePointRepository.findById(1)).thenReturn(Optional.of(curvePoint));
        when(curvePointRepository.save(any(CurvePoint.class))).thenReturn(curvePoint);

        CurvePoint updatedCurvePoint = curvePointService.updateCurvePoint(1, curvePointDTO);

        assertNotNull(updatedCurvePoint);
        assertEquals(curvePointDTO.getId(), updatedCurvePoint.getId());
        assertEquals(curvePointDTO.getTerm(), updatedCurvePoint.getTerm());
        assertEquals(curvePointDTO.getValue(), updatedCurvePoint.getValue());

        verify(curvePointRepository, times(1)).findById(1);
        verify(curvePointRepository, times(1)).save(any(CurvePoint.class));
    }

    @Test
    public void testDeleteCurvePoint() {
        when(curvePointRepository.findById(1)).thenReturn(Optional.of(curvePoint));

        curvePointService.deleteCurvPoint(1);

        verify(curvePointRepository, times(1)).delete(curvePoint);
    }

    @Test
    public void testDeleteCurvePoint_NotFound() {
        when(curvePointRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> curvePointService.deleteCurvPoint(1));
        assertEquals("could not find curve point by this id", exception.getMessage());

        verify(curvePointRepository, times(1)).findById(1);
    }
}
