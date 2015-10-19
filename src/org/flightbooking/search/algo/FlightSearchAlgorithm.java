package org.flightbooking.search.algo;

import java.util.List;

import org.flightbooking.domain.City;
import org.flightbooking.domain.Flight;
import org.flightbooking.search.FlightIternary;
import org.flightbooking.search.FlightSearchCriteria;

public interface FlightSearchAlgorithm {
	
	FlightIternary search(City srcCity, City destnCity, List<Flight> allFlights, FlightSearchCriteria searchCriteria);
}
