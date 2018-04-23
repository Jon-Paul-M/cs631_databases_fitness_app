package edu.njit.cs631.fitness.data.repository;

import java.util.List;

import edu.njit.cs631.fitness.data.entity.Member;
import org.springframework.data.repository.CrudRepository;

public interface MemberCrudRepository extends CrudRepository<Member, Integer> {

	List<Member> findByName(String name);

	Member findByEmail(String email);
}
