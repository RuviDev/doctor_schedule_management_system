document.addEventListener("DOMContentLoaded", function() {
    flatpickr("#shiftDate", { dateFormat: "Y-m-d" });
    flatpickr("#updateShiftDate", { dateFormat: "Y-m-d" });
});

function selectDepartment(button, department) {
    document.querySelectorAll('.btn-department').forEach(btn => btn.classList.remove('active'));
    button.classList.add('active');
    document.getElementById('selectedDepartment').innerText = department;
    localStorage.setItem('selectedDepartment', department);
}

// Function to load the selected department from localStorage on page load
function loadSelectedDepartment() {
    const savedDepartment = localStorage.getItem('selectedDepartment');
    if (savedDepartment) {
		id = document.getElementById('selectedDepartment').value;
        document.getElementById('selectedDepartment').innerText = savedDepartment;
        const buttons = document.querySelectorAll('.btn-department');
        buttons.forEach(btn => {
            if (btn.innerText === savedDepartment) {
                btn.classList.add('active');
                selectDepartment(btn, savedDepartment);
            }
        });
    }
}

// Call loadSelectedDepartment when the page loads
// window.onload = loadSelectedDepartment;


function validated(event) {
    let errorMessage = '';
    const shiftForm = document.getElementById('shiftForm');
    const today = new Date();
    const shiftDateInput = document.getElementById('shiftDate').value;
    const startTime = document.getElementById('startTime').value;
    const endTime = document.getElementById('endTime').value;
    const shiftType = document.getElementById('shiftTypeSelect').value;

    // Normalize the current date (today) to only date without time
    const todayNormalized = new Date(today.getFullYear(), today.getMonth(), today.getDate());
    const shiftDate = new Date(shiftDateInput);
    
    // Clear previous error messages
    document.getElementById('errorMessage').textContent = '';

    // Check if the selected date is not in the past
    if (shiftDate < todayNormalized) {
        errorMessage += 'Shift date cannot be a past date. | ';
    }

    // Define shift time constraints
    const timeConstraints = {
        'Morning': { start: '08:00:00', end: '16:00:00' },
        'Evening': { start: '16:00:00', end: '23:59:00' },
        'Night': { start: '00:00:00', end: '08:00:00' }
    };

    const shiftStart = timeConstraints[shiftType].start;
    const shiftEnd = timeConstraints[shiftType].end;

    // Compare shift start and end times with selected times
    if (startTime < shiftStart || endTime > shiftEnd) {
        errorMessage += `Shift time must be within the ${shiftType} shift (${shiftStart} - ${shiftEnd}). `;
    }

    // Check if end time is after start time
    if (endTime <= startTime) {
        errorMessage += 'End time must be after the start time. ';
    }

    // Display error or submit form manually if there are no errors
    if (errorMessage) {
        event.preventDefault();  // Prevent form submission
        document.getElementById('errorMessage').textContent = errorMessage;
    } else {
        // Manually submit the form if no errors
        shiftForm.submit();
    }
}

// Function to open the update window
function openUpdateWindow(id, dname, date, type, startTime, endTime, roomId) {
    // Set the form's action URL to include the shift ID for updating
    document.getElementById('updateForm').action = 'UpdateShiftServlet?id=' + id;
    
    // Populate the default values fields with the existing shift details
    document.getElementById('updateShiftDate').value = date;
	document.getElementById('updateDname').value = dname;
    document.getElementById('updateStartTime').value = startTime;
    document.getElementById('updateEndTime').value = endTime;
	document.getElementById('updateShiftType').value = type;
    document.getElementById('defRoom').value = "";
	
	// Populate the placeholders with the existing shift details
    document.getElementById('updateShiftDate').placeholder = date;
    document.getElementById('updateStartTime').placeholder = startTime;
    document.getElementById('updateEndTime').placeholder = endTime;
    document.getElementById('defRoom').innerHTML = roomId + "  |  (Select New Room)";

    // Show the popup window
    document.getElementById("updateWindow").style.display = "block";
    document.getElementById("overlay").style.display = "block";
}

// Function to close the update window
function closeUpdateWindow() {
    document.getElementById("updateWindow").style.display = "none";
    document.getElementById("overlay").style.display = "none";
}

// Confirm deletion
function deleteShift(id, type) {
    var del = confirm("Are you sure you want to delete this shift?");
	// Redirect to the servlet that handles the deletion
	if(del){
		window.location.href = 'DeleteShiftServlet?id=' + id + '&type=' + type;
	}
}

function validatedUpdate(event) {
    let errorMessage = '';
    const shiftForm = document.getElementById('updateForm');
    const today = new Date();
    const shiftDateInput = document.getElementById('updateShiftDate').value;
    const startTime = document.getElementById('updateStartTime').value;
    const endTime = document.getElementById('updateEndTime').value;
    const shiftType = document.getElementById('updateShiftType').value;

    // Normalize the current date (today) to only date without time
    const todayNormalized = new Date(today.getFullYear(), today.getMonth(), today.getDate());
    const shiftDate = new Date(shiftDateInput);
    
    // Clear previous error messages
    document.getElementById('errorMessageUpdate').textContent = '';

    // Check if the selected date is not in the past
    if (shiftDate < todayNormalized) {
        errorMessage += 'Shift date cannot be a past date. | ';
    }

    // Define shift time constraints
    const timeConstraints = {
        'Morning': { start: '08:00', end: '16:00' },
        'Evening': { start: '16:00', end: '23:59' },
        'Night': { start: '00:00', end: '08:00' }
    };

    const shiftStart = timeConstraints[shiftType].start;
    const shiftEnd = timeConstraints[shiftType].end;

    // Compare shift start and end times with selected times
    if (startTime < shiftStart || endTime > shiftEnd) {
        errorMessage += `Shift time must be within the ${shiftType} shift (${shiftStart} - ${shiftEnd}). `;
    }

    // Check if end time is after start time
    if (endTime <= startTime) {
        errorMessage += 'End time must be after the start time. ';
    }

    // Display error or submit form manually if there are no errors
    if (errorMessage) {
        event.preventDefault();  // Prevent form submission
        document.getElementById('errorMessageUpdate').textContent = errorMessage;
    } else {
        // Manually submit the form if no errors
        shiftForm.submit();
    }
}