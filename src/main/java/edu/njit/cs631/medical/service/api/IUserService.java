package edu.njit.cs631.medical.service.api;


import edu.njit.cs631.medical.data.entity.security.User;
import edu.njit.cs631.medical.web.error.UserAlreadyExistException;

import java.util.List;

public interface IUserService {

    User findUserByEmail(String email);

    User registerNewUserAccount(String email) throws UserAlreadyExistException;

    void saveRegisteredUser(User user);

    void deleteUser(User user);

    // TODO: A couple of items to implement on the tail end if there is time.
    // void createPasswordResetTokenForUser(User user, String token);
    // User getUserByPasswordResetToken(String token);
    // boolean checkIfValidOldPassword(User user, String password);

    User getUserByID(long id);

    void changeUserPassword(User user, String password);

    List<String> getUsersFromSessionRegistry();

}