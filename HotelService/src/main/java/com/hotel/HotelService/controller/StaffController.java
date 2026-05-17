package com.hotel.HotelService.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/staff")
public class StaffController {

    @GetMapping
    public ResponseEntity<List<String>> getStaff() {
        List<String> staff = List.of("John Doe", "Jane Smith");

        return ResponseEntity.ok(staff);
    }

}
