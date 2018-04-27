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
import edu.njit.cs631.fitness.web.error.PersonNotFoundException;
import edu.njit.cs631.fitness.web.error.UserAlreadyExistException;
import edu.njit.cs631.fitness.web.model.MemberModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;


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
    public Member registerNewMemberAccount(MemberModel model) throws UserAlreadyExistException {
        if (model == null) {
            logger.info("I heard a null member model!");
            return null;
        }

        if(findMemberByEmail(model.getEmail()) != null) {
            throw new UserAlreadyExistException("A member with that e-mail address already exists: " + model.getEmail());
        }

        Member member = new Member();
        Membership membership = membershipRepository.findOne(model.getMembership());
        member.setMembership(membership);

        // TODO: Create remaining member model and then add member via memberRepo
        return null;

    }

    @Override
    public Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email);
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
