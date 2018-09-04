package com.andreitop.newco.controller;

import com.andreitop.newco.dto.TripDto;

import java.io.Serializable;
import java.util.List;

public interface AbstractController<T extends TripDto, K extends Serializable> {

    List<T> findAll();

    T findById(K id);

    void create(T trip);

    void delete(K id);

    void update(T newTrip);
}
