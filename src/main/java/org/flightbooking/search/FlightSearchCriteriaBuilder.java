package org.flightbooking.search;

import org.flightbooking.search.FlightSearchCriteria.Preference;

public class FlightSearchCriteriaBuilder {
	
	Preference preference;

	public FlightSearchCriteria build() {
		return new FlightSearchCriteria(preference);
	}
	
	public FlightSearchCriteriaBuilder preference(Preference preference) {
		this.preference = preference;
		return this;
	}
	
}
