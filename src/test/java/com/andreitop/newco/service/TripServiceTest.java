package com.andreitop.newco.service;

import com.andreitop.newco.dto.TripDto;
import com.andreitop.newco.repository.TripRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@WebMvcTest(TripServiceImpl.class)
public class TripServiceTest {


    @Autowired
    TripServiceImpl tripService;

    @MockBean
    private TripRepository tripRepository;

    @Mock
    private TripDto tripDto;

    @Mock
    private List<TripDto> allTrips;

    @Test
    public void findTripById() {
        when(tripRepository.findById(1L)).thenReturn(tripDto);
        TripDto actualTripDto = tripService.findById(1L);
        assertEquals(tripDto, actualTripDto);
    }

    @Test
    public void findAllTrips(){
        when(tripRepository.findAll()).thenReturn(allTrips);
        List<TripDto> actualAllTrips = tripService.findAll();
        assertEquals(allTrips, actualAllTrips);
    }

    @Test
    public void deleteTrips(){
        doNothing().when(tripRepository).delete(1L);

        tripService.delete(1L);
        verify(tripRepository).delete(1L);
    }

    @Test
    public void SaveTrip(){
        doNothing().when(tripRepository).save(tripDto);

        tripService.save(tripDto);
        verify(tripRepository).save(tripDto);
    }

    @Test
    public void UpdateTrip(){
        doNothing().when(tripRepository).update(tripDto);

        tripService.update(tripDto);
        verify(tripRepository).update(tripDto);
    }


}
