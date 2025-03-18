package com.ruvindu.doctorshiftmanagement.model;

public class Shift {
    private int doctorId;
    private String shiftDate;
    private String startTime;
    private String endTime;
    private int room;
    private String shiftType;  // "Morning", "Evening", or "Night"
    
	public int getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}
	public String getShiftDate() {
		return shiftDate;
	}
	public void setShiftDate(String shiftDate) {
		this.shiftDate = shiftDate;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public int getRoom() {
		return room;
	}
	public void setRoom(int room) {
		this.room = room;
	}
	public String getShiftType() {
		return shiftType;
	}
	public void setShiftType(String shiftType) {
		this.shiftType = shiftType;
	}
}