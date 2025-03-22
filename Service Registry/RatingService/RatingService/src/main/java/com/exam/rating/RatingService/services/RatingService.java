package com.exam.rating.RatingService.services;

import com.exam.rating.RatingService.entities.Rating;

import java.util.List;

public interface RatingService {

    //create
    Rating create(Rating rating);

    //getAllRatings
    List<Rating> getRatings();

    //get all by user ID
    List<Rating> getRatingByUSerId(String userId);

    //get all by hotel
    List<Rating> getRatingByHotelId(String hotelId);
}
