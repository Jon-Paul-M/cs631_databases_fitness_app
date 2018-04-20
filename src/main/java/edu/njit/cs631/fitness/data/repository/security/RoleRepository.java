package edu.njit.cs631.fitness.data.repository.security;

import edu.njit.cs631.fitness.data.entity.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);

    @Override
    void delete(Role role);
}
