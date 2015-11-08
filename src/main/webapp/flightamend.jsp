<html>
	<head>
		<title>Flight Amend</title>
		<link rel="stylesheet" type="text/css" href="style.css" />
		<script src="jquery-1.11.3.js"></script>
		<script>
			$(document).ready(function(){
				
				$.ajax({
					type: "GET",
					url: 'rest/flights/' + <%=request.getParameter("id")%>,
					dataType: 'json',
					data: "",
					success: populateFlightTable,
					error: function (jqXHR, exception) {
						alert("Error fetching flight data from server. HTTP status = " + jqXHR.status)
					}
				})
			})
			
			function sendFlightUpdateRequest() {
				
				var flightData = new Object()
				flightData.id='<%=request.getParameter("id")%>'
				flightData.hops=$('#hops_text').val()
				flightData.fare=$('#fare').val()
				flightData.duration=$('#duration').val()
				
				$.ajax({	
					type: "POST",
					url: 'rest/flights/' + <%=request.getParameter("id")%>,
					contentType: "application/x-www-form-urlencoded",
					data: {'hops' : flightData.hops , 'fare' : flightData.fare, 'duration' : flightData.duration},
					success: function(flight) {
						alert("Flight amended successfully")
						populateFlightTable(flight)
					},
					error: function (jqXHR, exception) {
						alert("Error amending flight data. HTTP status = " + jqXHR.status)
					}
				})
			}
			
			function populateFlightTable(flight) {
				clearDisplay()
				
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
					.append($('<td/>').html("<input type='text' id='hops_text' value='" + flight.hops + "' />"))								
				); 
				
				$("#flight_display").find('tbody').append($('<tr/>')
					.append($('<td/>').html("Fare"))
					.append($('<td/>').html("<input type='text' id='fare' value='" + flight.fare + "' />"))								
				); 						
				
				$("#flight_display").find('tbody').append($('<tr/>')
					.append($('<td/>').html("Duration"))
					.append($('<td/>').html("<input type='text' id='duration' value='" + flight.duration + "' />"))								
				); 						
										
				displayFlight()
			}
			
			function clearDisplay() {
				$('#flight_display').find('tbody').remove();
				$('#flight_display').hide()
				$("#outputDiv").hide()	
			}
			
			function displayAllFlights() {
				$("#outputDiv").show()
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
				
		<div id="outputDiv" style="display:none">
			<table id="flight_display">
				<thead>
					<tr>
						<th>Attribute</th>
						<th>Value</th>					
					</tr>	
				</thead>
			</table>
		</div>
		<button id="amendFlightBtn" onclick="sendFlightUpdateRequest()">Amend Flight</button>
	</body>
</html>