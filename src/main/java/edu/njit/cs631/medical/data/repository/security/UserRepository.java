package edu.njit.cs631.medical.data.repository.security;

import edu.njit.cs631.medical.data.entity.security.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM USER u, PERSON p WHERE  u.PERSON_ID = p.PERSON_ID AND p.EMAIL = :email")
    User findByEmail(@Param("email") String email);

    @Override
    void delete(User user);
}
