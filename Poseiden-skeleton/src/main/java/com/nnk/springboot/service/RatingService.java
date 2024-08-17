package com.nnk.springboot.service;

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

    public List<RaitingDTO> displayAllRating() {
      return   ratingRepository.findAll().stream()
                .map(rating ->
                        {
                               return new RaitingDTO(rating.getId(),
                                       rating.getMoodysRating(),
                                       rating.getSandpRating(),
                                       rating.getFitchRating(),
                                       rating.getOrderNumber());
                        })
                .collect(toList());

    }
}
