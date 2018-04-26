package edu.njit.cs631.fitness.service.impl;

import java.util.List;

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
		// TODO add sort ?
		return clazzRepository.findAll(futureActiveSpecification);
	}

}
