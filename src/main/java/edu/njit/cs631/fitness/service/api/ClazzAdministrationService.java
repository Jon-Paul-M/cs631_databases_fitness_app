package edu.njit.cs631.fitness.service.api;

import java.time.LocalDateTime;

import edu.njit.cs631.fitness.data.entity.Clazz;
import edu.njit.cs631.fitness.web.error.ClassConflictException;
import edu.njit.cs631.fitness.web.model.ClazzModel;

public interface ClazzAdministrationService {
    void generateRandomClasses();
    void generateRandomRegistrations();
	Clazz createClass(Integer exerciseId, Integer instructorId, Integer roomId, LocalDateTime start, Integer duration)
            throws ClassConflictException;
    void deleteClazz(Integer clazzId);
	void registerUserForClass(Integer userId, Integer clazzId);
    void deregisterUserForClass(Integer userId, Integer clazzId);

    void editClass(ClazzModel clazzModel) throws ClassConflictException;
}
