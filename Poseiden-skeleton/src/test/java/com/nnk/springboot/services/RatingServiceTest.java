package com.nnk.springboot.services;

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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class RatingServiceTest {

    @MockBean
    private RatingRepository ratingRepository;

    @Autowired
    private RatingService ratingService;

    private Rating rating;
    private RatingDTO ratingDTO;

    @BeforeEach
    public void setUp() {
        rating = new Rating();
        rating.setId(1);
        rating.setFitchRating("Fitch A");
        rating.setMoodysRating("Moodys B");
        rating.setSandpRating("S&P C");
        rating.setOrderNumber(1);

        ratingDTO = new RatingDTO();
        ratingDTO.setId(1);
        ratingDTO.setFitchRating("Fitch A");
        ratingDTO.setMoodysRating("Moodys B");
        ratingDTO.setSandPRating("S&P C");
        ratingDTO.setOrder(1);
    }

    @Test
    public void testDisplayAllRating() {
        Rating rating1 = new Rating(1, "Moodys A", "S&P B", "Fitch C", 1);
        Rating rating2 = new Rating(2, "Moodys B", "S&P A", "Fitch B", 2);

        when(ratingRepository.findAll()).thenReturn(Arrays.asList(rating1, rating2));

        List<RatingDTO> result = ratingService.displayAllRating();

        assertEquals(2, result.size());
        assertEquals(rating1.getId(), result.get(0).getId());
        assertEquals(rating2.getId(), result.get(1).getId());

        verify(ratingRepository, times(1)).findAll();
    }

    @Test
    public void testAddRating() {
        when(ratingRepository.save(any(Rating.class))).thenReturn(rating);

        Rating result = ratingService.addRating(ratingDTO);

        assertNotNull(result);
        assertEquals(rating.getId(), result.getId());
        assertEquals(rating.getMoodysRating(), result.getMoodysRating());
        assertEquals(rating.getFitchRating(), result.getFitchRating());
        assertEquals(rating.getSandpRating(), result.getSandpRating());
        assertEquals(rating.getOrderNumber(), result.getOrderNumber());

        verify(ratingRepository, times(1)).save(any(Rating.class));
    }

    @Test
    public void testDisplayRatingById() {
        when(ratingRepository.findById(1)).thenReturn(Optional.of(rating));

        RatingDTO result = ratingService.displayRatingById(1);

        assertEquals(rating.getId(), result.getId());
        assertEquals(rating.getMoodysRating(), result.getMoodysRating());
        assertEquals(rating.getFitchRating(), result.getFitchRating());
        assertEquals(rating.getSandpRating(), result.getSandPRating());
        assertEquals(rating.getOrderNumber(), result.getOrder());

        verify(ratingRepository, times(1)).findById(1);
    }

    @Test
    public void testDisplayRatingById_NotFound() {
        when(ratingRepository.findById(1)).thenReturn(Optional.empty());

//        RuntimeException exception = assertThrows(RuntimeException.class, () -> ratingService.displayRatingById(1));
//        assertEquals("could not find rating by this id", exception.getMessage());
//
//        verify(ratingRepository, times(1)).findById(1);
        assertThrows(RuntimeException.class, () -> ratingService.displayRatingById(1));
    }

    @Test
    public void testUpdateRating() {
        when(ratingRepository.findById(1)).thenReturn(Optional.of(rating));
        when(ratingRepository.save(any(Rating.class))).thenReturn(rating);

        Rating updatedRating = ratingService.updateRating(1, ratingDTO);

        assertNotNull(updatedRating);
        assertEquals(ratingDTO.getId(), updatedRating.getId());
        assertEquals(ratingDTO.getMoodysRating(), updatedRating.getMoodysRating());
        assertEquals(ratingDTO.getFitchRating(), updatedRating.getFitchRating());
        assertEquals(ratingDTO.getSandPRating(), updatedRating.getSandpRating());
        assertEquals(ratingDTO.getOrder(), updatedRating.getOrderNumber());

        verify(ratingRepository, times(1)).findById(1);
        verify(ratingRepository, times(1)).save(any(Rating.class));
    }

    @Test
    public void testDeleteRating() {
        when(ratingRepository.findById(1)).thenReturn(Optional.of(rating));

        ratingService.deleteRating(1);

        verify(ratingRepository, times(1)).delete(rating);
    }

    @Test
    public void testDeleteRating_NotFound() {
        when(ratingRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> ratingService.deleteRating(1));
        assertEquals("could not find rating by this id", exception.getMessage());

        verify(ratingRepository, times(1)).findById(1);
    }
}
