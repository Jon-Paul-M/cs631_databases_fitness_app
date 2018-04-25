package edu.njit.cs631.fitness.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import edu.njit.cs631.fitness.data.entity.security.Privilege;
import edu.njit.cs631.fitness.data.entity.security.Role;
import edu.njit.cs631.fitness.data.entity.security.User;
import edu.njit.cs631.fitness.data.repository.security.PrivilegeRepository;
import edu.njit.cs631.fitness.data.repository.security.RoleRepository;
import edu.njit.cs631.fitness.data.repository.security.UserRepository;
import edu.njit.cs631.fitness.service.impl.FitnessUserDetailsService;

@Component
public class InitialDataSetup  implements ApplicationListener<ContextRefreshedEvent> {

	Logger logger = LoggerFactory.getLogger(FitnessUserDetailsService.class);
	
    private boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // API

    @Override
    @Transactional
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }

        // == create initial privileges
        final Privilege readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
        final Privilege writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");
        final Privilege passwordPrivilege = createPrivilegeIfNotFound("CHANGE_PASSWORD_PRIVILEGE");

        // == create initial roles
        final List<Privilege> adminPrivileges = new ArrayList<>(Arrays.asList(readPrivilege, writePrivilege, passwordPrivilege));
        final List<Privilege> userPrivileges = new ArrayList<>(Arrays.asList(readPrivilege, passwordPrivilege));
        final Role adminRole = createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        createRoleIfNotFound("ROLE_USER", userPrivileges);

        // == create initial user
        createUserIfNotFound("admin@test.com",
                "Admin",
                "password",
                new ArrayList<>(Arrays.asList(adminRole)));

        alreadySetup = true;
    }

    @Transactional
    private final Privilege createPrivilegeIfNotFound(final String name) {
        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilege = privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    private Role createRoleIfNotFound(final String name, final Collection<Privilege> privileges) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
        }
        role.setPrivileges(privileges);
        role = roleRepository.save(role);
        return role;
    }

    @Transactional
    private User createUserIfNotFound(final String email,
                                      final String name,
                                      final String password,
                                      final Collection<Role> roles) {
        User user = userRepository.findByEmail(email);
        if (user != null) return user;
        user = new User();
        user.setEmail(email);
        user.setName(name);
        String passwordHash = passwordEncoder.encode(password);
        logger.info("passwordHash: " + passwordHash);
		user.setPasswordHash(passwordHash);
        user.setEnabled(true);
        user.setTokenExpired(false);
        user.setRoles(roles);
        user = userRepository.save(user);
        return user;
    }

}
