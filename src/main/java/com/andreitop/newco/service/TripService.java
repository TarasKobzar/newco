package com.andreitop.newco.service;

import com.andreitop.newco.dto.TripDto;
import com.andreitop.newco.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class TripService {

    private final TripRepository tripRepository;

    @Autowired
    public TripService(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    @Transactional( propagation = Propagation.SUPPORTS,readOnly = true, isolation = Isolation.SERIALIZABLE)
    public List<TripDto> findAll() {
        return tripRepository.findAll();
    }

    @Transactional( propagation = Propagation.SUPPORTS,readOnly = true, isolation = Isolation.SERIALIZABLE)
    public TripDto findById(Long id) {
        return tripRepository.findById(id).orElse(null);
    }

    public void save(TripDto trip) {
        tripRepository.save(trip);
    }

    public void delete(Long id) {
        tripRepository.deleteById(id);
    }

    public void update(TripDto newTrip) {
        tripRepository.save(newTrip);
    }
}
