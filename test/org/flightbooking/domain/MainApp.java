package org.flightbooking.domain;

import org.flightbooking.search.FlightIternary;
import org.flightbooking.search.FlightSearch;
import org.flightbooking.search.FlightSearchCriteria;
import org.flightbooking.search.FlightSearchCriteria.Preference;
import org.flightbooking.search.FlightSearchCriteriaBuilder;

public class MainApp {
	
	public static void main(String[] args) {
		
		City srcCity = new City("Mumbai");
		Airport santaCruzAirport = new Airport("Santacruz");
		srcCity.addAirport(santaCruzAirport);
		
		City destnCity = new City("Cochin");
		Airport cochinAirport = new Airport("CIA");
		destnCity.addAirport(cochinAirport);
		
		FlightSearchCriteriaBuilder builder = new FlightSearchCriteriaBuilder().preference(Preference.MIN_HOP);
		FlightSearchCriteria searchCriteria = builder.build();
		
		FlightSearch flightSearch = new FlightSearch(srcCity,destnCity); 
		FlightIternary flightIternary = flightSearch.search(searchCriteria);
		flightIternary.print();
		
		/*if(flightIternary.isAvailable()){
			flightIternary.print();
		} else {
			System.out.printf("No flights exist between %s -> %s", srcCity, destnCity);
		}*/
	}

}
