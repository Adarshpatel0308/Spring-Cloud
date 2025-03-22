package com.example.hotel.HotelService.controllers;

import com.example.hotel.HotelService.entities.Hotel;
import com.example.hotel.HotelService.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/hotels")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @PostMapping
    public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel) {
        return new ResponseEntity<>(hotelService.create(hotel), HttpStatus.CREATED);
    }

    @GetMapping("/{hotelId}")
    public ResponseEntity<Hotel> getSingle(@PathVariable String hotelId) {
        return new ResponseEntity<>(hotelService.getSingle(hotelId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Hotel>> getAll() {
        return new ResponseEntity<>(hotelService.getAll(), HttpStatus.OK);
    }
}