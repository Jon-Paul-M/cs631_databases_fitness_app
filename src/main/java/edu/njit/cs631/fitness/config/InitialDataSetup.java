package edu.njit.cs631.fitness.config;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import edu.njit.cs631.fitness.data.entity.*;
import edu.njit.cs631.fitness.data.repository.HourlyInstructorRepository;
import edu.njit.cs631.fitness.data.repository.MemberRepository;
import edu.njit.cs631.fitness.data.repository.MembershipRepository;
import edu.njit.cs631.fitness.data.repository.SalariedInstructorRepository;
import edu.njit.cs631.fitness.service.api.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.access.method.P;
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
    private MembershipRepository membershipRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private UserService userService;

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
        final Privilege passwordPrivilege = createPrivilegeIfNotFound("CHANGE_ALL_PASSWORD_PRIVILEGE");
        final Privilege passwordSelfPrivilege = createPrivilegeIfNotFound("CHANGE_SELF_PASSWORD_PRIVILEGE");

        final Privilege admin = createPrivilegeIfNotFound("ADMIN");
        final Privilege registerMember = createPrivilegeIfNotFound("REGISTER_MEMBER");
        final Privilege registerInstructor = createPrivilegeIfNotFound("REGISTER_INSTRUCTOR");
        final Privilege registerSelfForClass = createPrivilegeIfNotFound("REGISTER_SELF_FOR_CLASS");
        final Privilege registerMemberForClass = createPrivilegeIfNotFound("REGISTER_MEMBER_FOR_CLASS");
        final Privilege createClass = createPrivilegeIfNotFound("CREATE_CLASS");
        final Privilege viewAllDetailsClass = createPrivilegeIfNotFound("VIEW_ALL_DETAILS_CLASS");
        final Privilege cancelClass = createPrivilegeIfNotFound("CANCEL_CLASS");
        final Privilege cancelOwnClass = createPrivilegeIfNotFound("CANCEL_OWN_CLASS");
        final Privilege deleteUsers = createPrivilegeIfNotFound("DELETE_MEMBER");
        final Privilege deleteClass = createPrivilegeIfNotFound("DELETE_CLASS");
        final Privilege viewAllMembers = createPrivilegeIfNotFound("VIEW_ALL_MEMBERS");
        final Privilege viewMembersOwnClass = createPrivilegeIfNotFound("VIEW_ALL_MEMBERS_OWN_CLASS");
        final Privilege modifyMemberDetails = createPrivilegeIfNotFound("MODIFY_ALL_MEMBERS"); // excludes password
        final Privilege modifySelfMemberDetails = createPrivilegeIfNotFound("MODIFY_SELF_MEMBER"); // excludes password

        // == create initial roles
        final List<Privilege> adminPrivileges = new ArrayList<>(Arrays.asList(
                admin,
                passwordPrivilege,
                passwordSelfPrivilege,
                registerMember,
                registerInstructor,
                registerSelfForClass,
                registerMemberForClass,
                createClass,
                cancelClass,
                cancelOwnClass,
                deleteUsers,
                deleteClass,
                viewAllMembers,
                viewMembersOwnClass,
                viewAllDetailsClass,
                modifyMemberDetails,
                modifySelfMemberDetails
        ));
        final List<Privilege> memberPrivileges = new ArrayList<>(Arrays.asList(
                passwordSelfPrivilege,
                registerSelfForClass,
                modifySelfMemberDetails
        ));
        final List<Privilege> instructorPrivileges = new ArrayList<>(Arrays.asList(
                passwordSelfPrivilege,
                modifySelfMemberDetails,
                cancelOwnClass,
                viewMembersOwnClass
        ));
        final Role adminRole = createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        final Role instructorRole = createRoleIfNotFound("ROLE_INSTRUCTOR", instructorPrivileges);
        final Role memberRole = createRoleIfNotFound("ROLE_MEMBER", memberPrivileges);

        // == create initial user
        userService.createUserIfNotFound("admin@test.com",
                "Admin",
                "password",
                new ArrayList<>(Arrays.asList(adminRole)));

        userService.createInstructorIfNotFound("hourlyinstructor@test.com",
                "Hourly Instructor",
                "password",
                new BigDecimal("1.00"),
                new ArrayList<>(Arrays.asList(instructorRole)),
                InstructorTypes.HOURLY);

        userService.createInstructorIfNotFound("salariedinstructor@test.com",
                "Salaried Instructor",
                "password",
                new BigDecimal("1.00"),
                new ArrayList<>(Arrays.asList(instructorRole)),
                InstructorTypes.SALARIED);


        userService.createInstructorIfNotFound("instructormember@test.com",
                "Hourly Instructor & Member",
                "password",
                new BigDecimal("1.00"),
                new ArrayList<>(Arrays.asList(instructorRole, memberRole)),
                InstructorTypes.HOURLY);

        userService.createMemberIfNotFound("member@test.com",
                "Regular Member",
                "password",
                new ArrayList<>(Arrays.asList(memberRole)),
                "Address 1",
                "Address 2",
                "City",
                "county",
                "State",
                "12345");

        alreadySetup = true;
    }

    @Transactional
    private Privilege createPrivilegeIfNotFound(final String name) {
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


}
