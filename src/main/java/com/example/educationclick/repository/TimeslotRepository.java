package com.example.educationclick.repository;

import com.example.educationclick.domain.Timeslot;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Timeslot entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TimeslotRepository extends JpaRepository<Timeslot, Long> {}
