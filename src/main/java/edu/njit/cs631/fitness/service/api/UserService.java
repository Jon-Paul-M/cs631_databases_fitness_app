package edu.njit.cs631.fitness.service.api;


import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import edu.njit.cs631.fitness.data.entity.Instructor;
import edu.njit.cs631.fitness.data.entity.InstructorTypes;
import edu.njit.cs631.fitness.data.entity.Member;
import edu.njit.cs631.fitness.data.entity.security.Role;
import edu.njit.cs631.fitness.data.entity.security.User;
import edu.njit.cs631.fitness.web.error.UserAlreadyExistException;
import edu.njit.cs631.fitness.web.error.UserNotFoundException;
import edu.njit.cs631.fitness.web.model.InstructorModel;
import edu.njit.cs631.fitness.web.model.MemberModel;

public interface UserService {

    // Generic User information (currently just the admin user role)
    User createUserIfNotFound(String email,
                              String name,
                              String password,
                              Collection<Role> roles);
    User findUser(String email);
    User findUser(Integer id);
    // User saveRegisteredUser(User user);
    // User changeUserPassword(User user, String password);

    // General members (those who can register for classes)
    void generateManyMembers();
    User createMemberIfNotFound(String email,
                                String name,
                                String password,
                                Collection<Role> roles,
                                String add1,
                                String add2,
                                String city,
                                String county,
                                String state,
                                String postalCode);
    Member findMemberByEmail(String email);
    Member registerNewMemberAccount(MemberModel model) throws UserAlreadyExistException;
    Member editMemberAccount(MemberModel model) throws UserAlreadyExistException, UserNotFoundException;
    void deleteMemberAccount(Integer id) throws UserNotFoundException;
    // void saveMember(Member member);


    // Instructor Users
    List<Instructor> listAllInstructors();
    void registerNewInstructorAccount(InstructorModel instructorModel);
    User createInstructorIfNotFound(
            String email,
            String name,
            String password,
            BigDecimal rate,
            Collection<Role> roles,
            InstructorTypes instructorType);
    Instructor findInstructor(String email);
    Instructor findInstructor(Integer id);
    // void saveInstructor(Instructor instructor);


    // TODO: A couple of items to implement on the tail end if there is time.
    // void createPasswordResetTokenForUser(User user, String token);
    // User getUserByPasswordResetToken(String token);
    // boolean checkIfValidOldPassword(User user, String password);
    // List<String> getUsersFromSessionRegistry();



}