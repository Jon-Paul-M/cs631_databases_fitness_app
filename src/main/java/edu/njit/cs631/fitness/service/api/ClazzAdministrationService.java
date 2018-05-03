package edu.njit.cs631.fitness.service.api;

import java.time.LocalDateTime;

import edu.njit.cs631.fitness.data.entity.Clazz;

public interface ClazzAdministrationService {
    void generateRandomClasses();
    void generateRandomRegistrations();
	Clazz createClass(Integer exerciseId, Integer instructorId, Integer roomId, LocalDateTime start, Double duration);
    void deleteClazz(Integer clazzId);
	void registerUserForClass(Integer userId, Integer clazzId);
    void deregisterUserForClass(Integer userId, Integer clazzId);
}
