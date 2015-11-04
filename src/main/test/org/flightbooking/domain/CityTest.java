package org.flightbooking.domain;

import java.util.LinkedList;
import java.util.Stack;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class CityTest {
	
	City mumbaiCity;
	Airport santaCruzAirport;
	Airport dupSantaCruzAirport;
	
	@Before
	public void setup() {
		mumbaiCity = new City("Mumbai");	
		santaCruzAirport = new Airport("Santacruz");
		dupSantaCruzAirport = new Airport("Santacruz");
	}
	
	@Test
	public void testNewAirportCanBeAdded() {
		
		City mumbaiCity = new City("Mumbai");	
		
		boolean added = mumbaiCity.addAirport(santaCruzAirport);
		Assert.assertTrue("Santacruz airport added to Mumbai city", added);

		boolean exists = mumbaiCity.getAirports().contains(dupSantaCruzAirport);
		Assert.assertTrue("Mumbai city should have Santacruz airport", exists);
	}
	
	@Test
	public void testDupAirportCannotBeAdded() {
		
		boolean added = mumbaiCity.addAirport(santaCruzAirport);
		Assert.assertTrue("Santacruz airport added to Mumbai city", added);
		
		added = mumbaiCity.addAirport(dupSantaCruzAirport);
		Assert.assertFalse("Santacruz airport can not be added twice to Mumbai city", added);
	}
	
	@Test
	public void test() {
		Stack<String> stack = new Stack<>();
		stack.push("1");
		stack.push("2");
		stack.push("3");
		System.out.println(new LinkedList<>(stack));
		
	}

}
