package edu.njit.cs631.fitness.service.impl;

import edu.njit.cs631.fitness.data.entity.*;
import edu.njit.cs631.fitness.data.entity.security.Role;
import edu.njit.cs631.fitness.data.entity.security.User;
import edu.njit.cs631.fitness.data.repository.HourlyInstructorRepository;
import edu.njit.cs631.fitness.data.repository.MemberRepository;
import edu.njit.cs631.fitness.data.repository.MembershipRepository;
import edu.njit.cs631.fitness.data.repository.SalariedInstructorRepository;
import edu.njit.cs631.fitness.data.repository.security.RoleRepository;
import edu.njit.cs631.fitness.data.repository.security.UserRepository;
import edu.njit.cs631.fitness.service.api.UserService;
import edu.njit.cs631.fitness.web.error.UserAlreadyExistException;
import edu.njit.cs631.fitness.web.error.UserNotFoundException;
import edu.njit.cs631.fitness.web.model.InstructorModel;
import edu.njit.cs631.fitness.web.model.MemberModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.sql.Timestamp;


@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    Logger logger = LoggerFactory.getLogger(FitnessUserDetailsService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MembershipRepository membershipRepository;

    @Autowired
    private HourlyInstructorRepository hourlyInstructorRepository;


    @Autowired
    private SalariedInstructorRepository salariedInstructorRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private MemberRepository personRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static int lastGenerated = 0;
    private final Random random = new Random();

    @Override
    @Transactional
    public void generateManyMembers() {
        Role member_role = roleRepository.findByName("ROLE_MEMBER");
        List<Membership> memberships = membershipRepository.findAll();
        for(int i = 0; i < 20; i++) {
            lastGenerated += i;
            Member member = new Member();
            member.setMembership(memberships.get(random.nextInt(memberships.size())));
            member.setEmail("member_" + lastGenerated + "@test.com");
            member.setName("member_" + lastGenerated);
            member.setPasswordHash(passwordEncoder.encode("password"));
            member.setAddress1("add1");
            member.setAddress2("");
            member.setCity("city");
            member.setCounty("county");
            member.setState("state");
            member.setPostalCode("postal");
            member.setRegistrationDate(
                    Timestamp.valueOf(
                            LocalDateTime.now()
                                    .minusHours(random.nextInt(24))
                                    .minusDays(random.nextInt(30)
                                    )
                    )
            );
            member.setEnabled(true);
            member.setRoles(Arrays.asList(member_role));
            memberRepository.save(member);
        }

        memberRepository.flush();
    }

    private void checkUserEmail(String email) throws UserAlreadyExistException {
        if(findUser(email) != null) {
            throw new UserAlreadyExistException("A user with that e-mail address already exists: " + email);
        }
    }

    @Override
    public User findUser(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findUser(Integer id) {
        return userRepository.findOne(id);
    }

    @Override
    public Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    @Override
    public Member registerNewMemberAccount(MemberModel memberDto) throws UserAlreadyExistException {
        if (memberDto == null) {
            logger.info("I heard a null member model!");
            return null;
        }

        if(findMemberByEmail(memberDto.getEmail()) != null) {
            throw new UserAlreadyExistException("A member with that e-mail address already exists: " + memberDto.getEmail());
        }

        Member member = new Member();
        member.setPasswordHash(passwordEncoder.encode("password"));
        member.setName(memberDto.getName());
        member.setRegistrationDate(Timestamp.valueOf(LocalDateTime.now()));
        member.setEmail(memberDto.getEmail());
        member.setEnabled(true);
        Membership membership = membershipRepository.findOne(memberDto.getMembership());
        member.setMembership(membership);

        // this is one reason I like the postfix of "dto" rather than "model"
        // because it's not clear if this is the UI model or the domain model
        member.setAddress1(memberDto.getAddress1());
        if (memberDto.getAddress2() != null) {
            member.setAddress2(memberDto.getAddress2());
        }

        member.setCity(memberDto.getCity());
        member.setState(memberDto.getState());
        member.setCounty(memberDto.getCounty());
        member.setPostalCode(memberDto.getPostalCode());
        member.setRoles(Arrays.asList(roleRepository.findByName("ROLE_MEMBER")));

        return memberRepository.save(member);
    }

    @Override
    public Member editMemberAccount(MemberModel memberDto) throws UserAlreadyExistException, UserNotFoundException {
        if (memberDto == null) {
            logger.info("I heard a null member model!");
            return null;
        }

        if (memberDto.getId() == null) {
            logger.info("I heard a member without an id");
            return null;
        }

        Member member = memberRepository.findOne(memberDto.getId());

        if (member == null) {
            logger.error("I heard an id of a non-existant member");
            throw new UserNotFoundException("A user with id " + memberDto.getId() + " was not found.");
        }

        User conflictingUser = userRepository.findByEmail(memberDto.getEmail());

        if (conflictingUser != null &&
                conflictingUser.getId() != null &&
                !conflictingUser.getId().equals(member.getId())) {
            logger.error("Conflciting user e-mail: " + conflictingUser.getEmail());
            throw new UserAlreadyExistException("Conflciting user e-mail: " + conflictingUser.getEmail());
        }

        // we don't change passwords or registration date or roles etc
        member.setName(memberDto.getName());
        member.setEmail(memberDto.getEmail());
        Membership membership = membershipRepository.findOne(memberDto.getMembership());
        member.setMembership(membership);

        member.setAddress1(memberDto.getAddress1());
        if (memberDto.getAddress2() != null) {
            member.setAddress2(memberDto.getAddress2());
        }

        member.setCity(memberDto.getCity());
        member.setState(memberDto.getState());
        member.setCounty(memberDto.getCounty());
        member.setPostalCode(memberDto.getPostalCode());

        return memberRepository.save(member);
    }

    /*
    @Override
    public User saveRegisteredUser(final User user) {
        return userRepository.save(user);
    }
    */


    /*
    @Override
    public void changeUserPassword(final User user, final String password) {
        user.setPasswordHash(passwordEncoder.encode(password));
        userRepository.save(user);
    }
    */


    @Override
    public void registerNewInstructorAccount(InstructorModel instructorModel) {

        checkUserEmail(instructorModel.getEmail());

        User user = createNewInstructor(instructorModel);
        user.setName(instructorModel.getName());
        user.setEmail(instructorModel.getEmail());
        user.setEnabled(true);
        user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_INSTRUCTOR")));
        user.setPasswordHash(passwordEncoder.encode("password"));

        userRepository.save(user);
    }

    private Instructor findInstructor(User user) {
        if (user == null) return null;
        Instructor instructor = hourlyInstructorRepository.findOne(user.getId());
        if (instructor != null) {
            return instructor;
        }
        return salariedInstructorRepository.findOne(user.getId());
    }

    @Override
    public Instructor findInstructor(Integer id) {
        return findInstructor(userRepository.findOne(id));
    }

    @Override
    public Instructor findInstructor(String email) {
        return findInstructor(userRepository.findByEmail(email));
    }

    @Override
    public List<Instructor> listAllInstructors() {
        List<HourlyInstructor> hourlyInstructors = hourlyInstructorRepository.findAll();
        List<SalariedInstructor> salariedInstructors = salariedInstructorRepository.findAll();
        List<Instructor> instructors = new ArrayList<>();
        for (HourlyInstructor hourlyInstructor : hourlyInstructors) {
            instructors.add(hourlyInstructor);
        }
        for (SalariedInstructor instructor : salariedInstructors) {
            instructors.add(instructor);
        }
        return instructors;
    }

    public User createNewInstructor(InstructorModel instructorModel) {
        Instructor instructor;
        BigDecimal rate = new BigDecimal(instructorModel.getWage());
        InstructorTypes instructorType = InstructorTypes.valueOf(instructorModel.getInstructorType());
        switch(instructorType) {
            case HOURLY:
                instructor = new HourlyInstructor();
                instructor.setWage(rate);
                instructor.setHours(new BigDecimal(0));
                return (User)instructor;
            case SALARIED:
                instructor = new SalariedInstructor();
                instructor.setWage(rate);
                instructor.setHours(new BigDecimal(0));
                return (User)instructor;
            default:
                throw new IllegalArgumentException("No type for " + instructorType);
        }
    }

    @Transactional
    private User createInstructor(BigDecimal rate, InstructorTypes instructorType) {
        Instructor instructor;
        switch(instructorType) {
            case HOURLY:
                instructor = new HourlyInstructor();
                instructor.setWage(rate);
                instructor.setHours(new BigDecimal(0));
                return (User)instructor;
            case SALARIED:
                instructor = new SalariedInstructor();
                instructor.setWage(rate);
                instructor.setHours(new BigDecimal(0));
                return (User)instructor;
            default:
                throw new IllegalArgumentException("No type for " + instructorType);
        }
    }

    @Override
    @Transactional
    public User createInstructorIfNotFound(
            final String email,
            final String name,
            final String password,
            final BigDecimal rate,
            final Collection<Role> roles,
            final InstructorTypes instructorType) {
        User user = userRepository.findByEmail(email);
        Instructor instructor;
        if (user != null) {
            switch(instructorType) {
                case HOURLY:
                    instructor = hourlyInstructorRepository.findOne(user.getId());
                    if (instructor == null) {
                        userRepository.delete(user);
                    } else {
                        return user;
                    }
                    break;
                case SALARIED:
                    instructor = salariedInstructorRepository.findOne(user.getId());
                    if (instructor == null) {
                        userRepository.delete(user);
                    } else {
                        return user;
                    }
                    break;
                default:
                    throw new IllegalArgumentException("New instructor type not implemented" + instructorType);
            }
            return user;
        }

        User instructoruser = createInstructor(rate, instructorType);
        instructoruser = saveUserDetails(instructoruser, email, name, password, roles, false);
        switch(instructorType) {
            case HOURLY:
                return hourlyInstructorRepository.save((HourlyInstructor) instructoruser);
            case SALARIED:
                return salariedInstructorRepository.save((SalariedInstructor) instructoruser);
            default:
                throw new IllegalArgumentException("New instructor type not implemented" + instructorType);
        }
    }

    @Transactional
    public User createUserIfNotFound(final String email,
                                      final String name,
                                      final String password,
                                      final Collection<Role> roles) {
        User user = userRepository.findByEmail(email);
        if (user != null) return user;
        return saveUserDetails(new User(), email, name, password, roles);
    }


    @Transactional
    public User createMemberIfNotFound(final String email,
                                        final String name,
                                        final String password,
                                        final Collection<Role> roles,
                                        final String address1,
                                        final String address2,
                                        final String city,
                                        final String county,
                                        final String state,
                                        final String postalCode) {
        User user = memberRepository.findByEmail(email);
        if (user != null) {
            Member member = memberRepository.findOne(user.getId());
            if (member == null) {
                // We have a user that is not a member, but we want to create that user as a member
                userRepository.delete(user);
                user = null;
            }
            return user;
        }
        Member member = new Member();
        Membership membership = membershipRepository.findAll().get(0);
        member.setMembership(membership);
        member.setAddress1(address1);
        member.setAddress2(address2);
        member.setCity(city);
        member.setCounty(county);
        member.setState(state);
        member.setPostalCode(postalCode);
        member.setRegistrationDate(Timestamp.valueOf(LocalDateTime.now()));
        saveUserDetails(member, email, name, password, roles, false);
        return memberRepository.save(member);
    }


    private User saveUserDetails(final User user,
                                 final String email,
                                 final String name,
                                 final String password,
                                 final Collection<Role> roles) {
        return saveUserDetails(user, email, name, password, roles, true);
    }

    private User saveUserDetails(final User user,
                                 final String email,
                                 final String name,
                                 final String password,
                                 final Collection<Role> roles,
                                 boolean saveUserDetails) {
        user.setEmail(email);
        user.setName(name);
        String passwordHash = passwordEncoder.encode(password);
        logger.info("passwordHash: " + passwordHash);
        user.setPasswordHash(passwordHash);
        user.setEnabled(true);
        user.setTokenExpired(false);
        user.setRoles(roles);
        if (saveUserDetails) {
            return userRepository.save(user);
        } else {
            return user;
        }
    }

}
