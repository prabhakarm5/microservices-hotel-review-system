package com.hotel.HotelService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hotel.HotelService.entities.Hotel;
import com.hotel.HotelService.services.HotelService;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/hotels")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    // create hotel
    @PostMapping()
    public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel) {

        return ResponseEntity.status(HttpStatus.CREATED).body(hotelService.createHotel(hotel));
    }

    // get single hotel
    @GetMapping("/{hotelId}")
    public ResponseEntity<Hotel> getHotel(@PathVariable String hotelId) {
        Hotel hotel = hotelService.getHotelById(hotelId);
        if (hotel != null) {
            return ResponseEntity.ok(hotel);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // get all hotels
    @GetMapping
    public ResponseEntity<Iterable<Hotel>> getAllHotels() {
        return ResponseEntity.ok(hotelService.getAllHotels());
    }
}
