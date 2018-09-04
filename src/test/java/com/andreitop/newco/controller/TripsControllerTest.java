package com.andreitop.newco.controller;

import com.andreitop.newco.common.ApiConstant;
import com.andreitop.newco.dto.TripDto;
import com.andreitop.newco.service.TripService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TripsController.class)
public class TripsControllerTest {

    private static final String TRIP_JSON = "{\"origin\": \"LED\" , \"destination\":\"MOW\", \"price\" : 12256}";
    private static final String CONTENT_TYPE = "application/json;charset=UTF-8";
    private static final String API_URL = ApiConstant.API_V_1 + "/trips";
    private static final String API_URL_WITH_ID = API_URL + "/1";


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TripService tripService;

    private List<TripDto> allTrips;

    public TripsControllerTest(){
        TripDto tripDto = new TripDto();
        tripDto.setId(1L);
        tripDto.setOrigin("MOW");
        tripDto.setDestination("LED");
        tripDto.setPrice(4232);
        allTrips = Collections.singletonList(tripDto);
    }

    @Test
    public void givenTrip_whenGetTripById_thenReturnJsonArray() throws Exception {
        given(tripService.findById(1L)).willReturn(allTrips.get(0));
        mockMvc.perform(get(API_URL_WITH_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.origin", is("MOW")))
                .andExpect(jsonPath("$.destination", is("LED")))
                .andExpect(jsonPath("$.price", is(4232)));

        verify(tripService).findById(1L);
        verifyNoMoreInteractions(tripService);
    }

    @Test
    public void givenTrips_whenGetTrips_thenReturnJsonArray() throws Exception {
        given(tripService.findAll()).willReturn(allTrips);
        mockMvc.perform(get(API_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].origin", is("MOW")))
                .andExpect(jsonPath("$[0].destination", is("LED")))
                .andExpect(jsonPath("$[0].price", is(4232)));

        verify(tripService).findAll();
        verifyNoMoreInteractions(tripService);
    }

    @Test
    public void whenPostTrip_thenCreateTrip() throws Exception {
        mockMvc.perform(post(API_URL)
                .contentType(CONTENT_TYPE)
                .content(TRIP_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void whenPutTrip_thenUpdateTrip() throws Exception {
        mockMvc.perform(put(API_URL)
                .contentType(CONTENT_TYPE)
                .content(TRIP_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteTrip_whenDeleteTrip_thenReturnJsonArray() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete(API_URL_WITH_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(tripService).delete(1L);
        verifyNoMoreInteractions(tripService);
    }
}
