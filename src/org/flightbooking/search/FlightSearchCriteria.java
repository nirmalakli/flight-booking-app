package org.flightbooking.search;

public class FlightSearchCriteria {
	
	public enum Preference  {
		MIN_HOP,
		MIN_FARE
	}
	
	private final Preference preference;

	public FlightSearchCriteria(Preference preference) {
		this.preference = preference;
	}
	
	public Preference getPreference() {
		return preference;
	}

}
