package edu.njit.cs631.medical.data.repository.security;

import edu.njit.cs631.medical.data.entity.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);

    @Override
    void delete(Role role);
}
