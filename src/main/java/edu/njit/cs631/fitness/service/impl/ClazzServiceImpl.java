package edu.njit.cs631.fitness.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import edu.njit.cs631.fitness.data.entity.Clazz;
import edu.njit.cs631.fitness.data.repository.ClazzRepository;
import edu.njit.cs631.fitness.data.specification.ClazzSpecifications;
import edu.njit.cs631.fitness.service.api.ClazzService;

@Service("clazzService")
public class ClazzServiceImpl implements ClazzService {
	
	@Autowired
	private ClazzRepository clazzRepository;

	public ClazzServiceImpl() {
		super();
	}

	@Override
	public List<Clazz> listFutureActiveClasses() {
		Specifications<Clazz> futureActiveSpecification = 
				Specifications.where(ClazzSpecifications.isActive()).and(ClazzSpecifications.isFuture());
		return clazzRepository.findAll(futureActiveSpecification);
	}

	@Override
	public List<Clazz> listSameInstructorClazzes(Integer clazzId) {

		Clazz clazz = clazzRepository.findOne(clazzId);

		if (clazz == null) {
			return new ArrayList<>();
		}

	    List<Clazz> future = listFutureActiveClasses();

		return future.stream()
                .filter(Objects::nonNull)
                .filter(c -> !c.getId().equals(clazzId))
                .filter(c -> c.getInstructor().getId().equals(clazz.getInstructor().getId()))
                .collect(Collectors.toList());

	}

	@Override
	public List<Clazz> listSameTimeWindowClazzes(Integer clazzId) {
        Clazz clazz = clazzRepository.findOne(clazzId);

        if (clazz == null) {
            return new ArrayList<>();
        }

        List<Clazz> future = listFutureActiveClasses();

        LocalDateTime clazzStart = clazz.getStart().toLocalDateTime();

        return future.stream()
                .filter(Objects::nonNull)
                .filter(c -> c.getId() != null && !c.getId().equals(clazzId))
                .filter(c -> {
                    LocalDateTime compareStart = c.getStart().toLocalDateTime();

                    return (compareStart.isAfter(clazzStart.minusHours(1)) &&
                               compareStart.isBefore(clazzStart.plusHours(1)));
                })
                .collect(Collectors.toList());
	}

	@Override
	public List<Clazz> listSameExerciseClazzes(Integer clazzId) {
        Clazz clazz = clazzRepository.findOne(clazzId);

        if (clazz == null) {
            return new ArrayList<>();
        }


        List<Clazz> future = listFutureActiveClasses();

        return future.stream()
                .filter(Objects::nonNull)
                .filter(c -> !c.getId().equals(clazzId))
                .filter(c -> c.getExercise().getId().equals(clazz.getExercise().getId()))
                .collect(Collectors.toList());
	}

}
