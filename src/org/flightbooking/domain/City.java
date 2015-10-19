package org.flightbooking.domain;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class City {

	private final String name;
	private final Set<Airport> airports;
	
	public City(String name) {
		this(name, Collections.emptyList());
	}
	
	public City(String name, Collection<Airport> airports) {
		this.name = name;
		this.airports = new CopyOnWriteArraySet<>(airports);
	}
	
	public String getName() {
		return name;
	}
	
	public Set<Airport> getAirports() {
		return Collections.unmodifiableSet(airports);
	}
	
	public boolean addAirport(Airport airport) {
		return airports.add(airport);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		City other = (City) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "City [name=" + name + ", airports=" + airports + "]";
	}
}
