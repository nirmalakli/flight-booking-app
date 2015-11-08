package org.flightbooking.dao;

import java.util.List;

import org.flightbooking.domain.Airport;
import org.flightbooking.domain.City;
import org.flightbooking.domain.Flight;

public interface FlightBookingDao {

	List<City> getCities();

	List<Airport> getAirports();

	List<Flight> getFlights();
	
	void updateFlight(Flight updatedFlight);

	void deleteFlight(Flight flight);

}
