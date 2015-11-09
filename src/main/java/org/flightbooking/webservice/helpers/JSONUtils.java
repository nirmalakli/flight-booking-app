package org.flightbooking.webservice.helpers;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.flightbooking.domain.Flight;

public class JSONUtils {
	
	public static String getFlightString(Flight flight) throws JsonGenerationException, JsonMappingException, IOException {
		return new ObjectMapper().writeValueAsString(flight);
	}
	
	public static String getFlightString(List<Flight> flights) throws JsonGenerationException, JsonMappingException, IOException {
		return new ObjectMapper().writeValueAsString(flights);
	}

}
