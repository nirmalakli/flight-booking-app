package org.flightbooking.webservice;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.flightbooking.dao.ClassPathFileBookingDao;
import org.flightbooking.dao.FlightBookingDao;
import org.flightbooking.domain.Flight;

@Path("/flights")
public class FlightResource {
	
	private FlightBookingDao flightBookingDao = ClassPathFileBookingDao.getInstance();
	private FlightsDaoHelper daoHelper = new FlightsDaoHelper(flightBookingDao);
	
	@GET
	@Produces (MediaType.APPLICATION_JSON)
	public Response getAllFlights() {
		
		return Response.status(200).entity(get()).build();
	}
	
	// URL: /flights/id
	@Path("{id}")
	@GET
	@Produces (MediaType.APPLICATION_JSON)
	public Response getFlightById(@PathParam("id") String flightId) {
		return Response.status(200).entity(get(flightId)).build();
	}
	
	// URL: /flights/id
	@Path("{id}")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces ("application/json")
	public Response updateFlight(
			@PathParam("id") String flightId, 
			@FormParam("hops") String hops, 
			@FormParam("fare") String fare,
			@FormParam("duration") String duration) {
		
		try {
			Flight flight = daoHelper.getFlight(flightId);
			
			Flight amendFlight = new Flight(
					flightId, 
					flight.getSource(), 
					flight.getDestination(), 
					flight.getAirline(), 
					Double.valueOf(fare), 
					Integer.valueOf(duration), 
					Integer.valueOf(hops));
			
			String flightData = JSONUtils.getFlightString(daoHelper.amendFlight(amendFlight));
			return Response.status(200).entity(flightData).build();
		} catch (Exception e) {
			return Response.status(500).entity("{'Error' : '" + e.getMessage() + "'}").build();
		}
	}
	
	// URL: /flights/id
	@Path("{id}")
	@DELETE
	@Produces ("application/json")
	public Response deleteFlight(@PathParam("id") String flightId) {
		
		// It's OK to send just the id for deletion
		daoHelper.deleteFlight(new Flight(flightId, null, null, null, 0, 0, 0));
		return Response.status(200).entity("{\"status\" : \"ok\"}").build();
	}
	
	
	String get() {
		List<Flight> flights = flightBookingDao.getFlights();
		try {
			return JSONUtils.getFlightString(flights);
		} catch (Exception e) {
			e.printStackTrace();
			return "{'Flights' : 'None bcoz - '" + e.getMessage() + "}";
		} 
	}
	
	String get(String id) {
		
		try {
			Flight flight = daoHelper.getFlight(id);
			return JSONUtils.getFlightString(flight);
		} catch (Exception e) {
			e.printStackTrace();
			return "{'Flight' : 'None bcoz - '" + e.getMessage() + "}";
		} 
	}
	
	/*String getFlightString(Flight flight) throws JsonGenerationException, JsonMappingException, IOException {
		return new ObjectMapper().writeValueAsString(flight);
	}
	

	String getFlightString(List<Flight> flights) throws JsonGenerationException, JsonMappingException, IOException {
		return new ObjectMapper().writeValueAsString(flights);
	}*/
	
/*	private Flight getFlight(String flightId) {
		List<Flight> flights = flightBookingDao.getFlights();
		for(Flight f : flights) {
			if(f.getId().equals(flightId)) {
				return f;
			}
		}
		return null;
	}
	
	private Flight amendFlight(Flight flight) {
		flightBookingDao.updateFlight(flight);
		return getFlight(flight.getId());
	}
	
	private void deleteFlight(Flight flight) {
		flightBookingDao.deleteFlight(flight);
	}*/
	
}
