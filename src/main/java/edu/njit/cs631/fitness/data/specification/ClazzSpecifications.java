package edu.njit.cs631.fitness.data.specification;


import java.util.Calendar;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import edu.njit.cs631.fitness.data.entity.Clazz;

public class ClazzSpecifications {

	public ClazzSpecifications() {
		super();
	}

	public static Specification<Clazz> isFuture() {
		return new Specification<Clazz>() {
			@Override
			public Predicate toPredicate(Root<Clazz> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				return builder.greaterThan(root.get("start"), Calendar.getInstance().getTime());
				//root.get(Clazz_.start)
				//return null;
			}
		};
	}
	
	public static Specification<Clazz> isActive() {
		return new Specification<Clazz>() {
			@Override
			public Predicate toPredicate(Root<Clazz> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				// TODO: if/when active flag added to clazz update this
				Predicate alwaysTrue = builder.and();
				return  alwaysTrue;
			}
		};
	}
}

