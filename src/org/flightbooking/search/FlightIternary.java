package org.flightbooking.search;

import java.util.Collections;
import java.util.List;

import org.flightbooking.domain.City;
import org.flightbooking.domain.Flight;

public class FlightIternary {
	
	private final City srcCity;
	private final City destnCity;
	private final List<Flight> flights;
	private final String errorMessage;

	public FlightIternary(City srcCity, City destnCity, List<Flight> flights) {
		this(srcCity, destnCity, flights, null);
	}
	
	public FlightIternary(City srcCity, City destnCity, List<Flight> flights, String errorMessage) {
		this.srcCity = srcCity;
		this.destnCity = destnCity;
		this.flights = flights;
		this.errorMessage = errorMessage;
	}

	public void print() {
		System.out.printf("Iternary from %s -> %s:%n", srcCity, destnCity);
		System.out.printf("Number of Steps: %d%n", flights.size());
		if(errorMessage != null && !errorMessage.isEmpty()) {
			System.err.println(errorMessage);
		} else {
			int count=0;
			for(Flight flight : flights) {
				System.out.println("Step - " + (++count) + " : " + flight);
			}
		}
	}
	
	public List<Flight> getFlights() {
		return Collections.unmodifiableList(flights);
	}
	
	public boolean isAvailable() {
		return !flights.isEmpty();
	}

}
