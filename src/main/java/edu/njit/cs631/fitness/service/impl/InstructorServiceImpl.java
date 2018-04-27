package edu.njit.cs631.fitness.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.njit.cs631.fitness.data.entity.HourlyInstructor;
import edu.njit.cs631.fitness.data.entity.Instructor;
import edu.njit.cs631.fitness.data.repository.HourlyInstructorRepository;
import edu.njit.cs631.fitness.service.api.InstructorService;

@Service("instructorService")
public class InstructorServiceImpl implements InstructorService {
	
	@Autowired
	private HourlyInstructorRepository hourlyInstructorRepository;

	public InstructorServiceImpl() {
		super();
	}


	@Override
	public List<Instructor> listAllInstructors() {
		List<HourlyInstructor> hourlyInstructors = hourlyInstructorRepository.findAll();
		List<Instructor> instructors = new ArrayList<>();
		for (HourlyInstructor hourlyInstructor : hourlyInstructors) {
			instructors.add(hourlyInstructor);
		}
		return instructors;
	}

}
