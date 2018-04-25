package edu.njit.cs631.fitness.service.api;

import java.util.Date;

import edu.njit.cs631.fitness.data.entity.Clazz;

public interface ClazzAdministrationService {
	public Clazz createClass(Integer exerciseId, Integer instructorId, Integer roomId, Date start, Integer duration);
}
