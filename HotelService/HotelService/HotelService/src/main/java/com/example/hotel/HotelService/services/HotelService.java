package com.example.hotel.HotelService.services;

import com.example.hotel.HotelService.entities.Hotel;

import java.util.List;

public interface HotelService {

    //create
    Hotel create(Hotel hotel);

    //getall
    List<Hotel> getAll();

    //get single
    Hotel getSingle(String id);
}
