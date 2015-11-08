package org.flightbooking.search.algo;

import java.util.Iterator;
import java.util.List;

import org.flightbooking.dao.ClassPathFileBookingDao;
import org.flightbooking.domain.Airport;
import org.flightbooking.domain.City;
import org.flightbooking.domain.Flight;
import org.flightbooking.search.FlightIternary;
import org.flightbooking.search.FlightSearchCriteria;
import org.flightbooking.search.FlightSearchCriteria.Preference;
import org.flightbooking.search.FlightSearchCriteriaBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DjksrtaShortestPathFlightSearchAlgorithmTest {

	private City srcCity;
	private City destnCity;
	private List<Flight> allFlights;
	private FlightSearchCriteria searchCriteria;
	private ClassPathFileBookingDao flightBookingDao;
	private DjksrtaShortestPathFlightSearchAlgorithm algo;
	
	@Before
	public void setup() {
		flightBookingDao = ClassPathFileBookingDao.getInstance();
		
		srcCity = getCity(flightBookingDao.getCities(), "Mumbai" );
		destnCity = getCity(flightBookingDao.getCities(), "Cochin" );
		
		allFlights = flightBookingDao.getFlights();
		
		FlightSearchCriteriaBuilder builder = new FlightSearchCriteriaBuilder().preference(Preference.MIN_HOP);
		searchCriteria = builder.build();
		
		algo = new DjksrtaShortestPathFlightSearchAlgorithm();
		
	}
	
	private City getCity(List<City> cities, String name) {
		for(City city : cities) {
			if(city.getName().equals(name)) {
				return city;
			}
		}
		
		return null;
	}

	@Test
	public void testThatAlgorithmWorksForConnnectedCities() {
		FlightIternary iternary = algo.search(srcCity, destnCity, allFlights, searchCriteria);
		
		Assert.assertTrue("Flight iternary should be available", iternary.isAvailable());
		
		Iterator<Flight> iterator = iternary.getFlights().iterator();
		Flight firstStep = iterator.next();
		Assert.assertEquals("First leg starts from Santacruz", firstStep.getSource(), new Airport("Santacruz"));
		Assert.assertEquals("First leg terminates at KIA", firstStep.getDestination(), new Airport("KIA"));
		
		Flight secondStep = iterator.next();
		Assert.assertEquals("Second leg starts from KIA", secondStep.getSource(), new Airport("KIA"));
		Assert.assertEquals("Second leg terminates at CIA", secondStep.getDestination(), new Airport("CIA"));
		
		Assert.assertFalse("Only 2 legs should exist", iterator.hasNext());
	}

	@Test
	public void testThatAlgorithmReturnsEmptyForUnConnectedCities() {
		
		srcCity = getCity(flightBookingDao.getCities(), "Chennai" );
		destnCity = getCity(flightBookingDao.getCities(), "Cochin" );
		
		DjksrtaShortestPathFlightSearchAlgorithm algo = new DjksrtaShortestPathFlightSearchAlgorithm();
		FlightIternary iternary = algo.search(srcCity, destnCity, allFlights, searchCriteria);
		
		Assert.assertFalse("Flight iternary should not be available", iternary.isAvailable());
	}
}
