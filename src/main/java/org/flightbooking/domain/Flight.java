package org.flightbooking.domain;

public class Flight {
	
	private final String id;
	private final Airport source;
	private final Airport destination;
	private final Airline airline;
	private final double fare;
	private final int duration;
	private final int hops;
	
	public Flight(String id, Airport source, Airport destination, Airline airline, double fare, int duration, int hops) {
		this.id = id;
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
	
	public String getId() {
		return id;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Flight other = (Flight) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Flight [source=" + source.getName() + ", destination=" + destination.getName() + "]";
	}
}
