package com.andreitop.newco.repository;

import com.andreitop.newco.dto.TripDto;

import java.io.Serializable;
import java.util.List;

public interface TripRepository<T extends TripDto, K extends Serializable> {
    List<T> findAll();

    T findById(final K id);

    void save(final T trip);

    void delete(final K id);

    void update(final T newTrip);
}
