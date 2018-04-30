package edu.njit.cs631.fitness.service.api;

import java.time.LocalDateTime;
import java.util.Date;

import edu.njit.cs631.fitness.data.entity.Clazz;
import edu.njit.cs631.fitness.data.entity.security.User;

public interface ClazzAdministrationService {
	Clazz createClass(Integer exerciseId, Integer instructorId, Integer roomId, LocalDateTime start, Integer duration);
	void registerUserForClass(Integer userId, Integer clazzId);
}
