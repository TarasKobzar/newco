package com.andreitop.newco.repository;


import com.andreitop.newco.dto.TripDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRepository extends JpaRepository<TripDto, Long> {
}
