package edu.njit.cs631.fitness.data.repository.security;

import edu.njit.cs631.fitness.data.entity.security.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

    Privilege findByName(String name);

    @Override
    void delete(Privilege role);
}
