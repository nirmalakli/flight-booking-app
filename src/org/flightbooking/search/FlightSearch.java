package org.flightbooking.search;

import java.util.List;

import org.flightbooking.dao.ClassPathFileBookingDao;
import org.flightbooking.dao.FlightBookingDao;
import org.flightbooking.domain.City;
import org.flightbooking.domain.Flight;
import org.flightbooking.search.algo.DjksrtaShortestPathFlightSearchAlgorithm;
import org.flightbooking.search.algo.FlightSearchAlgorithm;

public class FlightSearch {

	private final City srcCity;
	private final City destnCity;
	
	private FlightSearchAlgorithm searchAlgo = new DjksrtaShortestPathFlightSearchAlgorithm();
	private FlightBookingDao flightBookingDao = new ClassPathFileBookingDao();
	
	public FlightSearch(City srcCity, City destnCity) {
		this.srcCity = srcCity;
		this.destnCity = destnCity;
	}

	public FlightIternary search(FlightSearchCriteria searchCriteria) {
		List<Flight> flights = flightBookingDao.getFlights();
		return searchAlgo.search(srcCity, destnCity, flights, searchCriteria);
	}
	
	public void setSearchAlgo(FlightSearchAlgorithm searchAlgo) {
		this.searchAlgo = searchAlgo;
	}
	
	public void setFlightBookingDao(FlightBookingDao flightBookingDao) {
		this.flightBookingDao = flightBookingDao;
	}
}
