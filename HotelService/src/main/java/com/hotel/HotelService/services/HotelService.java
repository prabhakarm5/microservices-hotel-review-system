package com.hotel.HotelService.services;

import java.util.List;

import com.hotel.HotelService.entities.Hotel;

public interface HotelService {

    // create
    Hotel createHotel(Hotel hotel);

    // getall
    List<Hotel> getAllHotels();

    // get single
    Hotel getHotelById(String id);

}
