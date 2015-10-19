package org.flightbooking.domain;

public class Flight {
	
	private final Airport source;
	private final Airport destination;
	private final Airline airline;
	private final double fare;
	private final int duration;
	private final int hops;
	
	public Flight(Airport source, Airport destination, Airline airline, double fare, int duration, int hops) {
		this.source = source;
		this.destination = destination;
		this.airline = airline;
		this.fare = fare;
		this.duration = duration;
		this.hops = hops;
	}

	public Airport getSource() {
		return source;
	}

	public Airport getDestination() {
		return destination;
	}

	public Airline getAirline() {
		return airline;
	}

	public double getFare() {
		return fare;
	}

	public int getDuration() {
		return duration;
	}

	public int getHops() {
		return hops;
	}
	
	@Override
	public String toString() {
		return "Flight [source=" + source.getName() + ", destination=" + destination.getName() + "]";
	}
	
	
	
	
}
