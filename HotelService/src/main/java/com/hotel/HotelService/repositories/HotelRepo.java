package com.hotel.HotelService.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotel.HotelService.entities.Hotel;

public interface HotelRepo extends JpaRepository<Hotel, String> {

}

