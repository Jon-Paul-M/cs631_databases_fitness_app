package edu.njit.cs631.fitness.data.repository.security;

import edu.njit.cs631.fitness.data.entity.security.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u, Person p WHERE  u.person = p AND p.email = :email")
    User findByEmail(@Param("email") String email);

    @Override
    void delete(User user);
}
