<html>
	<head>
		<title>Flight Search</title>
		<link rel="stylesheet" type="text/css" href="style.css" />
		<script src="jquery-1.11.3.js"></script>
		<script>
			function sendFlightSearchRequest() {
				
				var flightData = new Object()
				flightData.id='<%=request.getParameter("id")%>'
				flightData.src=$('#srcInput').val()
				flightData.destn=$('#destnInput').val()
				
				clearDisplay()
				
				$.ajax({	
					type: "GET",
					url: 'rest/flightsearch/',
					contentType: "application/x-www-form-urlencoded",
					data: {'source' : flightData.src , 'destination' : flightData.destn},
					success: function(flights) {
						if(flights.length == 0) {
							alert("No flights found")
						} else {
							populateFlightIternary(flights)
						}
					},
					error: function (jqXHR, exception, error) {
						alert("Error searching flights. HTTP status = " + jqXHR.status + " " + jqXHR.responseText)
					}
				})
			}
			
			function populateFlightIternary(flights) {
				displayAllFlights()
				
				$("#resultsTable").append($('<tbody/>'))
				
				$.each(flights, function (index, flight) {
					
					$("#resultsTable").find('tbody').append($('<tr/>')
						.append($('<td/>').html(flight.id))
						.append($('<td/>').html(flight.source.name))
						.append($('<td/>').html(flight.destination.name))
						.append($('<td/>').html(flight.airline.name))
						.append($('<td/>').html(flight.fare))
						.append($('<td/>').html(flight.duration))
					); 
				})
			}
			
			function clearDisplay() {
				$('#resultsTable').find('tbody').remove();
				$('#resultsTable').hide()
				$("#outputDiv").hide()	
			}
			
			function displayAllFlights() {
				$("#outputDiv").show()
				$('#resultsTable').show()
			}
		</script>
	</head>
	<body>
		<a href="index.jsp">Home</a>
		<a href="flightadmin.jsp">Flight Admin</a>
		<a href="flightbooking.jsp">Flight Booking</a>
		<hr/>
		
		<table>
			<tr>
				<td>Enter source city:</td>
				<td><input type='text' id='srcInput'/></td>
			</tr>
			<tr>
				<td>Enter destination city:</td>
				<td><input type='text' id='destnInput'/></td>
			</tr>		
		</table>
		<hr/>
		<div id="outputDiv" style="display:none">
		<p> <b>Flight Itinerary:</b>
			<table id="resultsTable">
				<thead>
					<tr>
						<th>Flight ID</th>
						<th>Source Airport</th>
						<th>Destination Airport</th>
						<th>Airline</th>
						<th>Fare</th>
						<th>Duration</th>
					</tr>
				</thead>
			</table>
			<hr/>
		</div>
		<button id="searchFlightsBtn" onclick="sendFlightSearchRequest()">Search Flight</button>
	</body>
</html>