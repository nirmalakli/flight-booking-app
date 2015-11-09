package org.flightbooking.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.flightbooking.domain.Airline;
import org.flightbooking.domain.Airport;
import org.flightbooking.domain.City;
import org.flightbooking.domain.Flight;

public class ClassPathFileBookingDao implements FlightBookingDao {
	
	private static final ClassPathFileBookingDao instance;
	
	static {
		instance = new ClassPathFileBookingDao();
	}
	
	private final class FlightIDComparator implements Comparator<Flight> {
		@Override
		public int compare(Flight a, Flight b) {
			return a.getId().compareTo(b.getId());
		}
	}


	private List<City> cities;
	private List<Flight> flights;
	private List<Airport> airports;
	
	private ClassPathFileBookingDao() {
		loadCities();
		loadFlights();
		loadAirports();
	}
	

	private void loadCities() {
		
		cities = new ArrayList<>();
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(getInputStream("cities.txt")))) {
			String line = null;
			
			while( (line = reader.readLine()) != null) {
				if(!line.trim().isEmpty() || !line.startsWith("#")) {
					
					String[] tokens = line.split(",");
					City city = new City(tokens[0].trim());
					for(int i=1; i< tokens.length; i++){
						city.addAirport(new Airport(tokens[i].trim()));
					}
					cities.add(city);
				}
			}
		} catch (IOException e) {
			throw new RuntimeException("Error reading file cities.txt' from classpath.", e);
		}
	}
	
	private void loadAirports() {
		
		Collection<City> cities = getCities();
		Set<Airport> airports = new HashSet<>();
		for(City city : cities) {
			airports.addAll(city.getAirports());
		}
				
		this.airports = new ArrayList<Airport>(airports);
	}
	

	private void loadFlights() {
		
		flights = new ArrayList<>();
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(getInputStream("flights.txt")))) {
			String line = null;
			
			while( (line = reader.readLine()) != null) {
				if(!line.trim().isEmpty() || !line.startsWith("#") ) {
					
					String[] tokens = line.split(",");
					String id = tokens[0];
					Airport src = new Airport(tokens[1].trim());
					Airport destn = new Airport(tokens[2].trim());
					Airline airline = new Airline(tokens[3].trim(), Airline.Status.OPERATIONAL);
					double fare = Double.parseDouble(tokens[4].trim());
					int duration = Integer.parseInt(tokens[5].trim());
					int hops = Integer.parseInt(tokens[6].trim());
					
					flights.add(new Flight(id, src, destn, airline, fare, duration, hops));
				}
			}
		} catch (IOException e) {
			throw new RuntimeException("Error reading file flights.txt' from classpath.", e);
		}
		
		sortFlights();
	}
	
	@Override
	public List<City> getCities() {
		return Collections.unmodifiableList(cities);
	}
	
	@Override
	public synchronized java.util.List<Flight> getFlights() {
		return Collections.unmodifiableList(flights);
	}
	
	@Override
	public List<Airport> getAirports() {
		return Collections.unmodifiableList(airports);
	}
	
	InputStream getInputStream(String fileName) {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		return classLoader.getResourceAsStream(fileName);
	}
	
	public static void main(String[] args) {
	}


	@Override
	public synchronized void updateFlight(Flight updatedFlight) {
		flights.remove(updatedFlight);
		flights.add(updatedFlight);
		
		sortFlights();
	}

	@Override
	public synchronized void deleteFlight(Flight updatedFlight) {
		flights.remove(updatedFlight);
		sortFlights();
	}


	private void sortFlights() {
		Collections.sort(flights, new FlightIDComparator());
	}
	
	
	public static ClassPathFileBookingDao getInstance() {
		return instance;
	}
}
