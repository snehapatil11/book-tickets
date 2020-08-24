package com.booktickets.controller;

import com.booktickets.exception.ResourceNotFoundException;
import com.booktickets.model.UserBookings;
import com.booktickets.repository.UserBookingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserBookingsController {

    @Autowired
    UserBookingsRepository userBookingsRepository;

    @GetMapping("/userBookings")
    public List<UserBookings> getAllUserBookings() {
        return userBookingsRepository.findAll();
    }

    @PostMapping("/userBookings")
    public UserBookings createUserBookings(@Valid @RequestBody UserBookings userBookings) {
        return userBookingsRepository.save(userBookings);
    }

    @GetMapping("/userBookings/{id}")
    public UserBookings getUserBookingsById(@PathVariable(value = "id") Long userBookingId) {
        return userBookingsRepository.findById(userBookingId)
                .orElseThrow(() -> new ResourceNotFoundException("userBookings", "id", userBookingId));
    }

    @PutMapping("/userBookings/{id}")
    public UserBookings updateUserBookings(@PathVariable(value = "id") Long userBookingsId,
                                           @Valid @RequestBody UserBookings details) {

        UserBookings userBookings = userBookingsRepository.findById(userBookingsId)
                .orElseThrow(() -> new ResourceNotFoundException("UserBookings", "id", userBookingsId));

        userBookings.setNoOfBookings(details.getNoOfBookings());
        userBookings.setSeatNumber(details.getSeatNumber());
        userBookings.setPrice(details.getPrice());

        UserBookings updatedUserBookings = userBookingsRepository.save(userBookings);
        return updatedUserBookings;
    }

    @DeleteMapping("/userBookings/{id}")
    public ResponseEntity<?> deleteUserBookings(@PathVariable(value = "id") Long userBookingsId) {
        UserBookings userBookings = userBookingsRepository.findById(userBookingsId)
                .orElseThrow(() -> new ResourceNotFoundException("UserBookings", "id", userBookingsId));

        userBookingsRepository.delete(userBookings);

        return ResponseEntity.ok().build();
    }
}
