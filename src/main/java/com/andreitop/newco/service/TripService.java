package com.andreitop.newco.service;

import com.andreitop.newco.dto.TripDto;
import com.andreitop.newco.repository.TripRepository;

import java.io.Serializable;
import java.util.List;

public interface TripService<T extends TripDto, K extends Serializable> {
    List<T> findAll();

    T findById(K id);

    void save(T trip);

    void delete(K id);

    void update(T newTrip);
}
