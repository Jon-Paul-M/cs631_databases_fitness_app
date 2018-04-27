package edu.njit.cs631.fitness.service.impl;

import edu.njit.cs631.fitness.data.entity.Member;
import edu.njit.cs631.fitness.data.entity.Membership;
import edu.njit.cs631.fitness.data.entity.security.User;
import edu.njit.cs631.fitness.data.repository.MemberRepository;
import edu.njit.cs631.fitness.data.repository.MembershipRepository;
import edu.njit.cs631.fitness.data.repository.security.RoleRepository;
import edu.njit.cs631.fitness.data.repository.security.UserRepository;
import edu.njit.cs631.fitness.service.api.UserService;
import edu.njit.cs631.fitness.web.dto.UserDto;
import edu.njit.cs631.fitness.web.error.UserAlreadyExistException;
import edu.njit.cs631.fitness.web.model.MemberModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
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
    private RoleRepository roleRepository;

    @Autowired
    private MemberRepository personRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
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
    public User registerNewUserAccount(final UserDto userDto) throws UserAlreadyExistException {
        // Note, this will be used to look up an existing PersonID, and turn them into a User

        if (userDto == null) {
            logger.info("I heard a null user dto!");
            return null;
        }

        if(findUserByEmail(userDto.getEmail()) != null) {
            throw new UserAlreadyExistException("A user with that e-mail address already exists: " + userDto.getEmail());
        }

        final User user = new User();
        user.setPasswordHash(passwordEncoder.encode(userDto.getPassword()));
        user.setEnabled(true);
        user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));

        return userRepository.save(user);
    }

    @Override
    public void saveRegisteredUser(final User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUser(final User user) {
        // note, we will probably want to cascade deletion of user accounts if associated member account is deleted.
        userRepository.delete(user);
    }

    @Override
    public User getUserByID(long id) {
        return userRepository.findOne(id);
    }

    @Override
    public void changeUserPassword(final User user, final String password) {
        user.setPasswordHash(passwordEncoder.encode(password));
        userRepository.save(user);
    }

}
