package com.booktickets.repository;

import com.booktickets.model.UserBookings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserBookingsRepository extends JpaRepository<UserBookings, Long> {

}