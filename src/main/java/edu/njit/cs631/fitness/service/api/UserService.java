package edu.njit.cs631.fitness.service.api;


import edu.njit.cs631.fitness.data.entity.Instructor;
import edu.njit.cs631.fitness.data.entity.Member;
import edu.njit.cs631.fitness.data.entity.security.User;
import edu.njit.cs631.fitness.web.dto.UserDto;
import edu.njit.cs631.fitness.web.error.UserAlreadyExistException;
import edu.njit.cs631.fitness.web.model.InstructorModel;
import edu.njit.cs631.fitness.web.model.MemberModel;;

public interface UserService {

    User findUserByEmail(String email);

    Member findMemberByEmail(String email);

    Member registerNewMemberAccount(MemberModel model) throws UserAlreadyExistException;

    User registerNewUserAccount(UserDto model) throws UserAlreadyExistException;

    void saveRegisteredUser(User user);

    void deleteUser(User user);

    User getUserByID(long id);

    void changeUserPassword(User user, String password);

    void registerNewInstructorAccount(InstructorModel instructorModel);

    Instructor findInstructorByEmail(String s);

    // TODO: A couple of items to implement on the tail end if there is time.
    // void createPasswordResetTokenForUser(User user, String token);
    // User getUserByPasswordResetToken(String token);
    // boolean checkIfValidOldPassword(User user, String password);
    // List<String> getUsersFromSessionRegistry();



}