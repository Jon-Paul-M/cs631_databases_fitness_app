package edu.njit.cs631.fitness.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.njit.cs631.fitness.data.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Integer> {

	List<Member> findByName(String name);

	Member findByEmail(String email);
}
