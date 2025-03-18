<%@ page import="java.util.List" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page import="com.ruvindu.doctorshiftmanagement.model.Doctor" %>
<%@ page import="com.ruvindu.doctorshiftmanagement.model.Room" %>
<%@ page import="com.ruvindu.doctorshiftmanagement.model.ShiftTable" %>
<%@ include file="header.jsp" %>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Doctor Shift Management</title>
    <link rel="stylesheet" href="doctorShiftPage/styles.css">
    
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
    <script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
    <script src="https://cdn.jsdelivr.net/npm/lucide@latest/dist/umd/lucide.min.js"></script>
</head>
<body>
	<div class="bd">
	<!-- Check if the user is logged in -->
	<%
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		if(session.getAttribute("username") == null){
			response.sendRedirect("login.jsp");
		}
	%>
	
    <h1 class="text-3xl font-bold mb-4">Doctor Shift Management</h1>

    <!-- Department Selection -->
    <form id="deptForm" method="POST" action="DoctorServlet">
        <div class="mb-6 overflow-x-auto whitespace-nowrap dept" id="departmentButtons">
            <button class="btn-department" onclick="selectDepartment(this, 'ER')" type="submit" name="department" value="1">ER</button>
            <button class="btn-department" onclick="selectDepartment(this, 'ICU')" type="submit" name="department" value="2">ICU</button>
            <button class="btn-department" onclick="selectDepartment(this, 'OPD')" type="submit" name="department" value="3">OPD</button>
            <button class="btn-department" onclick="selectDepartment(this, 'Surgery')" type="submit" name="department" value="4">Surgery</button>
            <button class="btn-department" onclick="selectDepartment(this, 'Cardiology')" type="submit" name="department" value="5">Cardiology</button>
            <button class="btn-department" onclick="selectDepartment(this, 'Neurology')" type="submit" name="department" value="6">Neurology</button>
            <button class="btn-department" onclick="selectDepartment(this, 'Radiology')" type="submit" name="department" value="7">Radiology</button>
            <button class="btn-department" onclick="selectDepartment(this, 'Psychiatry')" type="submit" name="department" value="8">Psychiatry</button>
            <button class="btn-department" onclick="selectDepartment(this, 'Pharmacy')" type="submit" name="department" value="9">Pharmacy</button>
        </div>
    </form>


    <!-- Doctor Cards Section -->
    <div class="mb-6 overflow-x-auto whitespace-nowrap" id="doctorCards">
            <!-- Doctor cards are dynamically inserted using the DoctorServlet response object-->
            <%
                List<Doctor> doctors = (List<Doctor>) request.getAttribute("doctors");
                if (doctors != null) {
                    for (Doctor doctor : doctors) {
               %>
                   <div class="card-doctor">
                       <img src="<%= doctor.getProfilePic() %>" alt="<%= doctor.getName() %>" class="card-doctor__image">
                       <h3 class="font-semibold text-center">Dr. <%= doctor.getName() %></h3>
                       <p class="text-sm text-center"><%= doctor.getAvailableTime() %></p>
                   </div>
               <%
                    }
                }
            %>
     </div>
     
     
    <!-- Shift Assignment -->
    <div class="mb-6 p-4 border rounded-lg shiftassign">
        <h2 class="text-2xl font-bold mb-4">Shift Assignment (<span id="selectedDepartment"></span>)</h2>
        
        <form id="shiftForm" method="post" action="ShiftServlet">
		    <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
		
		        <div>
		            <input type="date" id="shiftDate" name="shiftDate" class="w-full p-2 border rounded" placeholder="Select Date" required>
		        </div>
		
		        <div class="space-y-4">
		            <!-- Get the list of doctors for the department as a dropDown from the DoctorServlet response object-->
		            <select id="doctorSelect" name="doctorId" class="w-full p-2 border rounded" required>
		                <option value="">Select Doctor</option>
		                <% 
		                    List<Doctor> doctors2 = (List<Doctor>) request.getAttribute("doctors");
		                    if (doctors2 != null) {
		                        for (Doctor doctor : doctors2) {
		                %>
		                    <option value="<%= doctor.getId() %>"><%= doctor.getName() %></option>
		                <% 
		                        }
		                    }
		                %>
		            </select>
		
		            <select id="shiftTypeSelect" name="shiftType" class="w-full p-2 border rounded" required>
		                <option value="">Select Shift Type</option>
		                <option value="Morning">Morning (8:00 AM - 4:00 PM)</option>
		                <option value="Evening">Evening (4:00 PM - 12:00 AM)</option>
		                <option value="Night">Night (12:00 AM - 8:00 AM)</option>
		            </select>
		
		            <input type="time" id="startTime" name="startTime" placeholder="Start Time" class="w-full p-2 border rounded" required>
		            <input type="time" id="endTime" name="endTime" placeholder="End Time" class="w-full p-2 border rounded" required>
		
		            <!-- Get the list of rooms for the department as a dropDown from the DoctorServlet response object-->
		            <select id="roomSelect" name="roomSelect" class="w-full p-2 border rounded">
		                <option value="">Select Room</option>
		                <% 
		                    List<Room> rooms = (List<Room>) request.getAttribute("rooms");
		                    if (rooms != null) {
		                        for (Room room : rooms) {
		                %>
		                    <option value="<%= room.getId() %>"><%= room.getRoomNumber() %></option>
		                <% 
		                        }
		                    }
		                %>
		            </select>
		
		            <button id="assignButton" type="submit" onclick="validated(event)" class="w-full p-2 btn1">Assign</button>
		
		            <!-- Error Message from the frontEnd validation-->
		            <p id="errorMessage" class="text-red-500"></p>
		
		            <!-- Success/Error Message from the ShiftServlet(backEnd) -->
		            <%
		                if (request.getAttribute("message") != null) {
		                    String message = (String) request.getAttribute("message");
		                    request.removeAttribute("message");
		                    if ("Shift successfully assigned!".equals(message)) {
		            %>			<p class="text-green-500"><%= message %></p>
		            <%		} else {
		            %>			<p class="text-red-500"><%= message %></p>
		            <%		}
		                }
		            %>
		
		        </div>
		
		    </div>
		</form>
    </div>


    <!-- Shift Table -->
    <div class="overflow-x-auto mb-6 p-4 border rounded-lg shift-table">
    	<h2 class="text-2xl font-bold mb-4">Shift Table</h2>
	    <table class="min-w-full">
	        <thead>
	            <tr>
	                <th class="px-4 py-2 text-left">Doctor Name</th>
	                <th class="px-4 py-2 text-left">Date</th>
	                <th class="px-4 py-2 text-left">Shift Type</th>
	                <th class="px-4 py-2 text-left">Time Range</th>
	                <th class="px-4 py-2 text-left">Room</th>
	                <th class="px-4 py-2 text-left">Update</th>
	                <th class="px-4 py-2 text-left">Delete</th>
	            </tr>
	        </thead>
	        <tbody id="shiftTableBody">
	        	
	        	<!-- Shifts for the department are dynamically taken from the DoctorServlet response object-->
	            <%
	                List<ShiftTable> shifts = (List<ShiftTable>) request.getAttribute("shifts");
	                if (shifts != null && !shifts.isEmpty()) {
	                    for (ShiftTable shiftT : shifts) {
	            %>
	                <tr>
	                    <td class="px-4 py-2">Dr. <%= shiftT.getDoctorName() %></td>
	                    <td class="px-4 py-2"><%= shiftT.getShiftDate() %></td>
	                    <td class="px-4 py-2"><%= shiftT.getShiftType() %></td>
	                    <td class="px-4 py-2"><%= shiftT.getFormattedTimeRange() %></td>
	                    <td class="px-4 py-2"><%= shiftT.getRoomNumber() %></td>
	                    <td class="px-4 py-2 icon">
	                        <button class="p-1" onclick="openUpdateWindow('<%= shiftT.getId() %>', '<%= shiftT.getDoctorName() %>', '<%= shiftT.getShiftDate() %>', '<%= shiftT.getShiftType() %>', '<%= shiftT.getStartTime() %>', '<%= shiftT.getEndTime() %>', '<%= shiftT.getRoomNumber() %>')">
	                            <img src="images/icons/edit-icon.svg" alt="Edit" class="w-4 h-4"> <!-- Update with your SVG icon path -->
	                        </button>
	                    </td>
	                    <td class="px-4 py-2 icon">
	                        <button class="p-1" onclick="deleteShift(<%= shiftT.getId() %>, '<%= shiftT.getShiftType() %>')">
	                            <img src="images/icons/delete-icon.svg" alt="Delete" class="w-4 h-4"> <!-- Update with your SVG icon path -->
	                        </button>
	                    </td>
	                </tr>
	            <%
	                    }
	                } else {
	            %>
	                <tr>
	                    <td colspan="7" class="px-4 py-2 text-red-500 text-center"><b>No shifts available</b></td>
	                </tr>
	            <%
	                }
	            %>
	        </tbody>
	    </table>
	</div>


    <!-- Update Window -->
    <div id="updateWindow" class="modal-update">
        <div class="flex justify-between items-center mb-4">
            <h3 class="text-xl font-bold">Update Shift</h3>
            <button onclick="closeUpdateWindow()" class="text-gray-500 hover:text-gray-700">
                <img src="images/icons/close.png" alt="&times;" class="w-4 h-4">
            </button>
        </div><br>
        
	    <form id="updateForm" method="post" action="UpdateShiftServlet">
	        <input type="text" id="updateShiftDate" name="udate" class="w-full p-2 border rounded mb-4" placeholder="Update Date">	        
	        <input type="time" id="updateStartTime" name="ustartTime" placeholder="Start Time" class="w-full p-2 border rounded mb-4">
         	<input type="time" id="updateEndTime" name="uendTime" placeholder="End Time" class="w-full p-2 border rounded mb-4">
         	<input type="text" id="updateDname" name="udname" hidden>
         	<input type="text" id="updateShiftType" name="utype" hidden>
	        
	        <select id="updateroomSelect" name="uroomSelect" class="w-full p-2 border rounded" required>
	            <option id="defRoom" value="">Select Room</option>
	            <% 
	                List<Room> rooms2 = (List<Room>) request.getAttribute("rooms");
	                if (rooms2 != null) {
	                    for (Room room : rooms2) {
	            %>
	                <option value="<%= room.getId() %>"><%= room.getRoomNumber() %></option>
	            <% 
	                    }
	                }
	            %>
	        </select><br><br>
	        
	        <button class="w-full p-2 btn1" id="updateButton" type="submit" onclick="validatedUpdate(event)">Update</button>
	        
	        <!-- Error Message from the frontEnd validation-->
            <br><br><p id="errorMessageUpdate" class="text-red-500"></p>

        </form>
    </div>

    <div class="overlay" id="overlay" onclick="closeUpdateWindow()"></div>
    </div>
    
    <script>
    	// departments are selected and the backend error messages are clered upon reloading
	    window.onload = function() {
	        <% 
	            // Retrieve the message from the session
	            String message = (String) request.getSession().getAttribute("messageU");
	            if (message != null) {
	                // Clear the message from the session after retrieving it
	                request.getSession().removeAttribute("messageU"); 
	        %>
	            alert("<%= message %>");
	        <% 
	            } 
	        %>
	        loadSelectedDepartment(); // get the department even the page refreshed
	    };
	</script>

    
    <script src="doctorShiftPage/script.js"></script>

</body>
</html>
