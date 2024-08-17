package com.nnk.springboot.service;

import com.nnk.springboot.domain.*;
import com.nnk.springboot.domain.dto.*;
import com.nnk.springboot.repositories.*;
import org.springframework.stereotype.*;

import java.util.*;

import static java.util.stream.Collectors.toList;

@Service
public class RatingService {
    private final RatingRepository ratingRepository;

    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    public List<RatingDTO> displayAllRating() {
      return   ratingRepository.findAll().stream()
                .map(rating ->
                        {
                               return new RatingDTO(rating.getId(),
                                       rating.getMoodysRating(),
                                       rating.getSandpRating(),
                                       rating.getFitchRating(),
                                       rating.getOrderNumber());
                        })
                .collect(toList());

    }

    public Rating addRating(RatingDTO ratingDTO) {
        Rating rating = new Rating();
        rating.setFitchRating(ratingDTO.getFitchRating());
        rating.setMoodysRating(ratingDTO.getMoodysRating());
        rating.setSandpRating(ratingDTO.getSandPRating());
        rating.setOrderNumber(ratingDTO.getOrder());
        return ratingRepository.save(rating);
    }
}
