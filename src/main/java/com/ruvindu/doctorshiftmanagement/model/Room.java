package com.ruvindu.doctorshiftmanagement.model;

public class Room {
    private int id;
    private String roomNumber;

    public Room(int id, String roomNumber) {
        this.id = id;
        this.roomNumber = roomNumber;
    }

    public int getId() {
        return id;
    }
    
	public String getRoomNumber() {
	        return roomNumber;
	    }

    public void setId(int id) {
		this.id = id;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	
}
