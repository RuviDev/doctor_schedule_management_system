package com.ruvindu.doctorshiftmanagement.dao;

import java.util.List;

import com.ruvindu.doctorshiftmanagement.model.Room;

public interface RoomDAO {
    List<Room> getRoomsByDepartment(int departmentId);
}

