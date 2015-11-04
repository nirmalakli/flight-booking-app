package org.flightbooking.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.flightbooking.domain.Airline;
import org.flightbooking.domain.Airport;
import org.flightbooking.domain.City;
import org.flightbooking.domain.Flight;

public class ClassPathFileBookingDao implements FlightBookingDao{

	@Override
	public List<City> getCities() {
		
		URL url = ClassLoader.getSystemClassLoader().getResource("cities.txt");
		List<City> cities = new ArrayList<>();
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
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
		return cities;
	}
	
	@Override
	public List<Airport> getAirports() {
		
		Collection<City> cities = getCities();
		Set<Airport> airports = new HashSet<>();
		for(City city : cities) {
			airports.addAll(city.getAirports());
		}
				
		return new ArrayList<Airport>(airports);
	}
	

	@Override
	public List<Flight> getFlights() {
		
		URL url = ClassLoader.getSystemClassLoader().getResource("flights.txt");
		System.out.println(url);
		List<Flight> flights = new ArrayList<>();
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
			String line = null;
			
			while( (line = reader.readLine()) != null) {
				if(!line.trim().isEmpty() || !line.startsWith("#") ) {
					
					String[] tokens = line.split(",");
					Airport src = new Airport(tokens[0].trim());
					Airport destn = new Airport(tokens[1].trim());
					Airline airline = new Airline(tokens[2].trim(), Airline.Status.OPERATIONAL);
					double fare = Double.parseDouble(tokens[3].trim());
					int duration = Integer.parseInt(tokens[4].trim());
					int hops = Integer.parseInt(tokens[5].trim());
					
					flights.add(new Flight(src, destn, airline, fare, duration, hops));
				}
			}
		} catch (IOException e) {
			throw new RuntimeException("Error reading file flights.txt' from classpath.", e);
		}
		return flights;
	}
	
	public static void main(String[] args) {
	}
	
}
