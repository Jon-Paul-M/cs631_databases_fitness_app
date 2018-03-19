package edu.njit.cs631.medical.data.repository.security;

import edu.njit.cs631.medical.data.entity.security.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    @Override
    void delete(User user);
}
