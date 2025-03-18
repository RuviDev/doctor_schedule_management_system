package com.ruvindu.doctorshiftmanagement.dao;

import java.util.List;

import com.ruvindu.doctorshiftmanagement.model.Doctor;

public interface DoctorDAO {
	List<Doctor> getDoctorsByDepartment(int depId);
}
