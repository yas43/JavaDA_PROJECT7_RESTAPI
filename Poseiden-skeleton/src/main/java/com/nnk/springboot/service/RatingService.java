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

    /**
     * find  list of rating  , convert to ratingDTO
     * @return the list of ratingDTO
     */
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

    /**
     * adding new rating to database , convert ratingDTO to bisList
     * @param ratingDTO given rating by user
     * @return accepted rating save in DB
     */
    public Rating addRating(RatingDTO ratingDTO) {
        Rating rating = new Rating();
        rating.setFitchRating(ratingDTO.getFitchRating());
        rating.setMoodysRating(ratingDTO.getMoodysRating());
        rating.setSandpRating(ratingDTO.getSandPRating());
        rating.setOrderNumber(ratingDTO.getOrder());
        return ratingRepository.save(rating);
    }

    /**
     * find rating by via id , convert to ratingDTO
     * @param id id
     * @return the  corresponding rating  or issue ratingNotFoundException
     */
    public RatingDTO displayRatingById(Integer id) {
        Rating rating = ratingRepository.findById(id)
                .orElseThrow(()->new RuntimeException("could not find rating by this id"));
        RatingDTO ratingDTO = new RatingDTO();
        ratingDTO.setId(rating.getId());
        ratingDTO.setFitchRating(rating.getFitchRating());
        ratingDTO.setMoodysRating(rating.getMoodysRating());
        ratingDTO.setSandPRating(rating.getSandpRating());
        ratingDTO.setOrder(rating.getOrderNumber());
        return ratingDTO;
    }

    /**
     * Updates an existing rating object with the provided new values.
     * @param id the ID of the rating object to update.
     * @param ratingDTO bid the rating object containing the new values.
     * @return the updated rating object saved to the database.
     *  @throws RuntimeException if the provided ID does not match any object in the database
     */
    public Rating updateRating(Integer id, RatingDTO ratingDTO) {
        Rating rating = ratingRepository.findById(id)
                .orElseThrow(()->new RuntimeException("could not find rating by given id"));
        rating.setId(ratingDTO.getId());
        rating.setSandpRating(ratingDTO.getSandPRating());
        rating.setFitchRating(ratingDTO.getFitchRating());
        rating.setMoodysRating(ratingDTO.getMoodysRating());
        rating.setOrderNumber(ratingDTO.getOrder());
        return ratingRepository.save(rating);
    }

    /**
     * Deletes a rating object by its ID.
     * @param id the ID of the rating object to delete.
     */
    public void deleteRating(Integer id) {
        Rating rating = ratingRepository.findById(id)
                .orElseThrow(()->new RuntimeException("could not find rating by this id"));
        ratingRepository.delete(rating);
    }
}
