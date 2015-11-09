package org.flightbooking.webservice.helpers;

import java.util.List;

import org.flightbooking.dao.FlightBookingDao;
import org.flightbooking.domain.Flight;

public class FlightsDaoHelper {
	
	private final FlightBookingDao flightBookingDao;
	
	public FlightsDaoHelper(FlightBookingDao flightBookingDao) {
		this.flightBookingDao = flightBookingDao;
	}

	public Flight getFlight(String flightId) {
		List<Flight> flights = flightBookingDao.getFlights();
		for(Flight f : flights) {
			if(f.getId().equals(flightId)) {
				return f;
			}
		}
		return null;
	}
	
	public  Flight amendFlight(Flight flight) {
		flightBookingDao.updateFlight(flight);
		return getFlight(flight.getId());
	}
	
	public void deleteFlight(Flight flight) {
		flightBookingDao.deleteFlight(flight);
	}

}
