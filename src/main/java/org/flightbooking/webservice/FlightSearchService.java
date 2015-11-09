package org.flightbooking.webservice;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.flightbooking.dao.ClassPathFileBookingDao;
import org.flightbooking.dao.FlightBookingDao;
import org.flightbooking.domain.City;
import org.flightbooking.search.FlightIternary;
import org.flightbooking.search.FlightSearch;
import org.flightbooking.search.FlightSearchCriteria;
import org.flightbooking.search.FlightSearchCriteria.Preference;
import org.flightbooking.webservice.helpers.JSONUtils;

@Path("/flightsearch")
public class FlightSearchService {
	
	private FlightBookingDao flightBookingDao = ClassPathFileBookingDao.getInstance();
	
	@GET
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces ("application/json")
	public Response search(
			@QueryParam("source") String source, 
			@QueryParam("destination") String destination) {
		
		try {
			
			City srcCity = getCity(source);
			City destnCity = getCity(destination);
			
			FlightSearch flightSearch = new FlightSearch(srcCity , destnCity);
			FlightIternary flightIternary = flightSearch.search(new FlightSearchCriteria(Preference.MIN_HOP));
			
			 String response = JSONUtils.getFlightString(flightIternary.getFlights());
			
			return Response.status(200).entity(response).build();
		} catch (Exception e) {
			return Response.status(500).entity("{\"Error\" : \"" + e.getMessage() + "\"}").build();
		}
	}

	private City getCity(String cityName) {

		for(City city : flightBookingDao.getCities()) {
			
			if(city.getName().equalsIgnoreCase(cityName.trim())) {
				return city;
			}
		}
		
		throw new IllegalArgumentException("No such city - '" + cityName + "' exists.");
	}
}
