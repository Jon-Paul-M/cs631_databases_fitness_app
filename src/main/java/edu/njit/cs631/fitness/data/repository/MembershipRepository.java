package edu.njit.cs631.fitness.data.repository;

import edu.njit.cs631.fitness.data.entity.Membership;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MembershipRepository extends JpaRepository<Membership, Integer> {

    Membership findOneByMembershipType(String membershipType);
}
