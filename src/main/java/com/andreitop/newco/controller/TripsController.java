package com.andreitop.newco.controller;

import com.andreitop.newco.common.ApiConstant;
import com.andreitop.newco.dto.TripDto;
import com.andreitop.newco.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(ApiConstant.API_V_1 + "/trips")
public class TripsController implements AbstractController<TripDto, Long> {

    private final TripService tripService;

    @Autowired
    public TripsController(TripService tripService) {
        this.tripService = tripService;
    }

    @Override
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TripDto> findAll() {
        return tripService.findAll();
    }

    @Override
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TripDto findById(@PathVariable("id") final Long id) {
        return tripService.findById(id);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody final TripDto trip) {
        tripService.save(trip);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") final Long id) {
        tripService.delete(id);
    }

    @Override
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void update(@Valid @RequestBody final TripDto newTrip) {
        tripService.update(newTrip);
    }

}
