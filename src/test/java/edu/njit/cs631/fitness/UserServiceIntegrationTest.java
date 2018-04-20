package edu.njit.cs631.fitness;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.assertj.core.util.Lists;
import org.junit.Test;
import org.springframework.test.context.jdbc.Sql;

import edu.njit.cs631.fitness.data.entity.Person;
import edu.njit.cs631.fitness.data.entity.security.User;
import edu.njit.cs631.fitness.testutils.BaseTest;
import edu.njit.cs631.fitness.web.dto.UserDto;
import edu.njit.cs631.fitness.web.error.UserAlreadyExistException;

public class UserServiceIntegrationTest extends BaseTest {

    Random random = new Random();

    @Test
    @Sql(scripts = {"classpath:/truncate_all.sql", "classpath:/data.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void givenRandomPerson_whenPromoted_thenCorrect() {
        User user = promoteRandomPersonToUser();

        assertNotNull(user);
        assertNotNull(user.getPerson().getEmail());
        assertNotNull(user.getId());
    }


    @Test(expected = UserAlreadyExistException.class)
    @Sql(scripts = {"classpath:/truncate_all.sql", "classpath:/data.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void givenRandomPerson_whenPromoted_thenThrowsAlreadyExists() {
        User user = promoteRandomPersonToUser();

        assertNotNull(user);
        assertNotNull(user.getPerson().getEmail());
        assertNotNull(user.getId());

        Person person = user.getPerson();
        promotePersonToUser(person);
    }

    private User promoteRandomPersonToUser() {
        return promotePersonToUser(getRandomPerson());
    }

    private Person getRandomPerson() {
        List<Person> people = Lists.newArrayList(personCrudRepository.findAll());
        Collections.shuffle(people, random);
        while(people.get(0).getId() == 133) { // one person in the default data is already a user.
            Collections.shuffle(people, random);
        }
        return people.get(0);
    }

    private UserDto createUserDto(Person person) {
        return createUserDto(person.getEmail());
    }

    private UserDto createUserDto(String email) {
        final UserDto userDto = new UserDto();
        userDto.setEmail(email);
        userDto.setPassword("SecretPassword");
        userDto.setMatchingPassword("SecretPassword");
        userDto.setRole(0);
        return userDto;
    }

    private User promotePersonToUser(Person person) {
        final String email = person.getEmail();
        final UserDto userDto = createUserDto(email);
        final User user = userService.registerNewUserAccount(userDto);
        assertNotNull(user);
        assertNotNull(user.getId());
        assertEquals(email, user.getPerson().getEmail());
        return user;
    }

}
