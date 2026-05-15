package com.hotel.HotelService.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.hotel.HotelService.Exception.ResourceNotFoundException;
import com.hotel.HotelService.entities.Hotel;
import com.hotel.HotelService.repositories.HotelRepo;
import com.hotel.HotelService.services.HotelService;

@Service
public class HotelServiceImpl implements HotelService {

    private HotelRepo hotelRepo;

    public HotelServiceImpl(HotelRepo hotelRepo) {
        this.hotelRepo = hotelRepo;
    }

    // create
    @Override
    public Hotel createHotel(Hotel hotel) {
        String id = UUID.randomUUID().toString();
        hotel.setId(id);
        return hotelRepo.save(hotel);
    }

    // get all hotels
    @Override
    public List<Hotel> getAllHotels() {
        return hotelRepo.findAll();
    }

    // get single hotel
    @Override
    public Hotel getHotelById(String id) {
        return hotelRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + id));
    }

}
