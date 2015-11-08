<html>
	<head>
		<title>Flight Admin Console</title>
		<link rel="stylesheet" type="text/css" href="style.css" />
		<script src="jquery-1.11.3.js"></script>
		<script>
			$(document).ready(function(){
				clearDisplay()
									
				$.ajax({
					type: "GET",
					url: 'rest/flights',
					dataType: 'json',
					data: "",
					success: function (flights) {
						
						$("#resultsTable").append($('<tbody/>'))
						
						$.each(flights, function (index, flight) {
							
							$("#resultsTable").find('tbody').append($('<tr/>')
								.append($('<td/>').html(flight.id))
								.append($('<td/>').html(flight.source.name))
								.append($('<td/>').html(flight.destination.name))
								.append($('<td/>').html(flight.airline.name))
								.append($('<td/>').html("<button id='viewFlight' onclick=viewFlight('" + flight.id + "')>View Flight </button>"))
								.append($('<td/>').html("<a href='flightamend.jsp?id=" + flight.id + "')>Amend Flight </button>"))
								.append($('<td/>').html("<button id='deleteFlight' onclick=deleteFlight('" + flight.id + "')>Delete Flight </button>"))
							); 
						})
						
						displayAllFlights()
					}
				 })					
			});			
			
			function viewFlight(flightId) {

				clearDisplay()
				
				$.ajax({
					type: "GET",
					url: 'rest/flights/' + flightId,
					dataType: 'json',
					data: "",
					success: function (flight) {
						
						$("#flight_display").append($('<tbody/>'))
											
						$("#flight_display").find('tbody').append($('<tr/>')
							.append($('<td/>').html("Flight Id"))
							.append($('<td/>').html(flight.id))								
						); 
						
						$("#flight_display").find('tbody').append($('<tr/>')
							.append($('<td/>').html("Source Airport"))
							.append($('<td/>').html(flight.source.name))								
						); 
						
						$("#flight_display").find('tbody').append($('<tr/>')
							.append($('<td/>').html("Destination Airport"))
							.append($('<td/>').html(flight.destination.name))								
						); 
						
						$("#flight_display").find('tbody').append($('<tr/>')
							.append($('<td/>').html("Airline Name"))
							.append($('<td/>').html(flight.airline.name))								
						); 
						
						$("#flight_display").find('tbody').append($('<tr/>')
							.append($('<td/>').html("Hops"))
							.append($('<td/>').html(flight.hops))								
						); 
						
						$("#flight_display").find('tbody').append($('<tr/>')
							.append($('<td/>').html("Fare"))
							.append($('<td/>').html(flight.fare))								
						); 						
						
						$("#flight_display").find('tbody').append($('<tr/>')
							.append($('<td/>').html("Duration"))
							.append($('<td/>').html(flight.duration))								
						); 						
												
						displayFlight()
					}
				 })	
			}
			
			function deleteFlight(flightId) {

				$.ajax({
					type: "DELETE",
					url: 'rest/flights/' + flightId,
					dataType: 'json',
					success: function (flight) {
						alert("Flight successfully deleted"),
						location.reload()
					},
					error: function (jqXHR, exception) {
						alert("Error deleting flight. HTTP status = " + jqXHR.status)
					}
				})
			}
				
			function clearDisplay() {
				$('#resultsTable').find('tbody').remove();
				$('#resultsTable').hide()
				$('#flight_display').find('tbody').remove();
				$('#flight_display').hide()
				$("#outputDiv").hide()	
			}
			
			function displayAllFlights() {
				$("#outputDiv").show()
				$('#resultsTable').show()
			}
			
			function displayFlight() {
				$("#outputDiv").show()
				$('#flight_display').show()
			}
						
		</script>
	</head>
	<body>
		<a href="index.jsp">Home</a>
		<a href="flightadmin.jsp">Flight Admin</a>
		<a href="flightbooking.jsp">Flight Booking</a>
		<hr/>
				
		<!-- button id="allFlightsBtn">View All Flights</button-->	
		
		<div id="outputDiv" style="display:none">
			<table id="resultsTable">
				<thead>
					<tr>
						<th>Flight ID</th>
						<th>Source Airport</th>
						<th>Destination Airport</th>
						<th>Airline</th>
						<th>Action</th>
						<th>Action</th>
						<th>Action</th>
					</tr>
				</thead>
			</table>
			<table id="flight_display">
				<thead>
					<tr>
						<th>Attribute</th>
						<th>Value</th>					
					</tr>	
				</thead>
			</table>
		</div>
	</body>
</html>