package edu.njit.cs631.fitness.config;

import edu.njit.cs631.fitness.data.entity.Person;
import edu.njit.cs631.fitness.data.entity.security.Privilege;
import edu.njit.cs631.fitness.data.entity.security.Role;
import edu.njit.cs631.fitness.data.entity.security.User;
import edu.njit.cs631.fitness.data.repository.PersonCrudRepository;
import edu.njit.cs631.fitness.data.repository.security.PrivilegeRepository;
import edu.njit.cs631.fitness.data.repository.security.RoleRepository;
import edu.njit.cs631.fitness.data.repository.security.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Component
public class InitialDataSetup  implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PersonCrudRepository personRepository;

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
                "Admin",
                "password",
                "000-00-0000",
                Person.Gender.F,
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
    private final Role createRoleIfNotFound(final String name, final Collection<Privilege> privileges) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
        }
        role.setPrivileges(privileges);
        role = roleRepository.save(role);
        return role;
    }

    private class PersonUser {
        final public Person person;
        final public User user;

        public PersonUser(Person p, User u) {
            this.person = p;
            this.user = u;
        }
    }

    @Transactional
    private final PersonUser createUserIfNotFound(final String email,
                                                  final String firstName,
                                                  final String lastName,
                                                  final String password,
                                                  final String ssn,
                                                  final Person.Gender gender,
                                                  final Collection<Role> roles) {
        User user = userRepository.findByEmail(email);
        Person newPerson;
        if (user == null) {
            newPerson = new Person();
            newPerson.setFirstName(firstName);
            newPerson.setLastName(lastName);
            newPerson.setSsn(ssn);
            newPerson.setGender(gender);
            newPerson.setEmail(email);
            newPerson = personRepository.save(newPerson);
            user = new User();
            user.setPerson(newPerson);
            user.setPasswordHash(passwordEncoder.encode(password));
            user.setEnabled(true);
            user.setTokenExpired(false);
            user.setRoles(roles);
            user = userRepository.save(user);
        } else {
            newPerson = user.getPerson();
        }
        return new PersonUser(newPerson, user);
    }

}
