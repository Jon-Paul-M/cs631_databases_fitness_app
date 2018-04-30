package edu.njit.cs631.fitness.data.repository.security;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.njit.cs631.fitness.data.entity.security.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);

    @Override
    void delete(User user);
}
