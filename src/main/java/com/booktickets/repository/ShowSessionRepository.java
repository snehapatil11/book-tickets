package com.booktickets.repository;

import com.booktickets.model.ShowSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ShowSessionRepository extends JpaRepository<ShowSession, Long> {

}