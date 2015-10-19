package org.flightbooking.search.algo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;

import org.flightbooking.domain.Airport;
import org.flightbooking.domain.City;
import org.flightbooking.domain.Flight;
import org.flightbooking.search.FlightIternary;
import org.flightbooking.search.FlightSearchCriteria;
import org.flightbooking.search.FlightSearchCriteria.Preference;

public class DjksrtaShortestPathFlightSearchAlgorithm implements FlightSearchAlgorithm{

	@Override
	public FlightIternary search(City srcCity, City destnCity, List<Flight> allFlights, FlightSearchCriteria searchCriteria) {
		
		Graph graph = new Graph(allFlights);
		
		// TODO: Add support for searching via multiple airports.
		// For now, just pick the first airport in srcCity and destnCity
		
		if(srcCity.getAirports().isEmpty()) {
			return new FlightIternary(srcCity, destnCity, Collections.emptyList(), "No flights start from " + srcCity );
		}
		
		if(destnCity.getAirports().isEmpty()) {
			return new FlightIternary(srcCity, destnCity, Collections.emptyList(), "No flights terminate at " + destnCity );
		}
		
		Airport srcAirport = srcCity.getAirports().iterator().next();
		Airport destnAirport = destnCity.getAirports().iterator().next();
		
		int sourceNode = graph.getIndex(srcAirport);
		int destnNode = graph.getIndex(destnAirport);
		
		if(sourceNode == -1) {
			return new FlightIternary(srcCity, destnCity, Collections.emptyList(), "No flights start from " + srcCity );
		}
		
		if(destnNode == -1) {
			return new FlightIternary(srcCity, destnCity, Collections.emptyList(), "No flights terminate at " + destnCity );
		}
		
		ShortestPath path = new ShortestPath(graph, sourceNode, searchCriteria);
		
		if(!path.hasPath(destnNode)) {
			return new FlightIternary(srcCity, destnCity, Collections.emptyList(), "No flights with the input search criteria");
		} else {
			
			// Trace the paths from destination airport 
			// back to the source airport. Then reverse the path.
			Stack<Flight> stack = new Stack<>();
			Flight flight = null;
			Airport from = null;
			
			int traceNode = destnNode;
			
			do{
				flight = path.edgeTo[traceNode];
				from = flight.getSource();
				
				stack.push(flight);
				
				traceNode = graph.getIndex(from);
			} while(!from.equals(srcAirport));
			
			// LinkedList should have elements in reverse order.
			LinkedList<Flight> flights = new LinkedList<>();
			while(!stack.isEmpty()) {
				flights.add(stack.pop());
			}
			return new FlightIternary(srcCity, destnCity, flights);
		}
	}
	
	static class Graph {
		
		List<Flight>[] adjFlightList;
		int vertices;
		int edges;
		
		// Map of airport names vs graph vertices
		private final Map<String, Integer> map = new HashMap<>();
		
		@SuppressWarnings("unchecked")
		public Graph(List<Flight> flights) {
			
			Set<String> airports = new HashSet<>();
			for(Flight flight : flights) {
				airports.add(flight.getSource().getName());
				airports.add(flight.getDestination().getName());
			}
			
			int count=0;
			for(String airport : airports) {
				map.put(airport, count++);
			}
			
			vertices = airports.size();
			
			this.adjFlightList = new ArrayList[vertices];
			for(int i=0; i< vertices; i++){
				adjFlightList[i] = new ArrayList<>();
			}
			
			for(Flight flight : flights) {
				int sourceIndex = map.get(flight.getSource().getName());
				int destnIndex = map.get(flight.getDestination().getName());
				
				addFlight(sourceIndex, destnIndex, flight);
			}
		}

		void addFlight(int src, int destn, Flight flight) {
			adjFlightList[src].add(flight);
			edges++;
		}
		
		public Iterable<Flight> adj(int src)  {
			return adjFlightList[src];
		}
		
		public Iterable<Flight> adj(Flight f)  {
			return adj(getSourceIndex(f));
		}
		
		public int getSourceIndex(Flight f) {
			return getIndex(f.getSource());
		}
		
		public int getDestinationIndex(Flight f) {
			return getIndex(f.getDestination());
		}
		
		public int getIndex(Airport a) {
			
			Integer value = map.get(a.getName());
			if(value == null) {
				return -1;
			} else {
				return value;
			}
		}
	}
	
	static class ShortestPath {

		Graph graph;
		double distTo[];
		Flight edgeTo[];
		
		int source;
		FlightSearchCriteria searchCriteria;
		
		PriorityQueue<PriorityQueueItem> pq;
		
		public ShortestPath(Graph graph, int source, FlightSearchCriteria searchCriteria) {
			this.graph = graph;
			this.source = source;
			this.searchCriteria = searchCriteria;
			
			distTo = new double[graph.vertices];
			edgeTo = new Flight[graph.vertices];
			
			for(int i=0; i< graph.vertices; i++) {
				distTo[i] = Double.POSITIVE_INFINITY;
				edgeTo[i] = null;
			}
			
			distTo[source] = 0;
			
			pq = new PriorityQueue<>();
			
			pq.add(new PriorityQueueItem(source, 0));
			
			while(!pq.isEmpty()) {
				PriorityQueueItem item = pq.remove();
				process(graph, item);
			}
		}

		private void process(Graph graph, PriorityQueueItem item) {
			for(Flight flight : graph.adj(item.node)) {
				
				int from = graph.getSourceIndex(flight);
				int to = graph.getDestinationIndex(flight);
				 
				double distanceFrom = distTo[from];
				double distanceTo = distTo[to];
				
				double distance = getDistance(flight);
				
				if(distanceTo > distanceFrom + distance) {
					distTo[to] = distanceFrom + distance;
					edgeTo[to] = flight;
					
					if(pq.contains(to)) {
						pq.remove(new PriorityQueueItem(to, 0));
					}
					pq.add(new PriorityQueueItem(to, distTo[to]));
				}
			}
		}
		
		private double getDistance(Flight flight) {
			if(this.searchCriteria.getPreference() == Preference.MIN_HOP) {
				// Each flight adds to just one hop
				return 1;
			} else {
				throw new UnsupportedOperationException("Only MIN_HOP searching is supported as of now!"); 
			}
		}

		public boolean hasPath(int destnNode) {
			return edgeTo[destnNode] != null;
		}
	}
	
	static class PriorityQueueItem implements  Comparable<PriorityQueueItem> {

		int node;
		double weight;
		
		public PriorityQueueItem(int node, double weight) {
			this.node = node;
			this.weight = weight;
		}

		@Override
		public int compareTo(PriorityQueueItem arg0) {

			return Double.compare(weight, arg0.weight);
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + node;
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
			PriorityQueueItem other = (PriorityQueueItem) obj;
			if (node != other.node)
				return false;
			return true;
		}
	}
}
