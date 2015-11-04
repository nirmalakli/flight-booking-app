package org.flightbooking.domain;

public class Airline {
	
	private final String name;
	private final Status status;
	
	public enum Status {
		OPERATIONAL,
		NON_OPERATIONAL
	}

	public Airline(String name, Status status) {
		this.name = name;
		this.status = status;
	}
	
	public String getName() {
		return name;
	}
	
	public Status getStatus() {
		return status;
	}

}
