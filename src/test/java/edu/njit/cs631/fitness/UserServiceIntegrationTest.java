package edu.njit.cs631.fitness;


import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.assertj.core.util.Lists;
import org.junit.Test;
import org.springframework.test.context.jdbc.Sql;

import edu.njit.cs631.fitness.data.entity.Member;
import edu.njit.cs631.fitness.testutils.BaseTest;
import edu.njit.cs631.fitness.web.dto.UserDto;

public class UserServiceIntegrationTest extends BaseTest {

    Random random = new Random();

    @Test
    @Sql(scripts = {"classpath:/truncate_all.sql", "classpath:/data-default.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void givenRandomPerson_whenPromoted_thenCorrect() {
        /* TODO: Rework test as default is user<-> member 1:1
        User user = promoteRandomPersonToUser();

        assertNotNull(user);
        assertNotNull(user.getEmail());
        assertNotNull(user.getId());
        */
    }


    @Test // (expected = UserAlreadyExistException.class)
    @Sql(scripts = {"classpath:/truncate_all.sql", "classpath:/data-default.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void givenRandomPerson_whenPromoted_thenThrowsAlreadyExists() {
        /* TODO: Rework test as default is user<-> member 1:1
        User user = promoteRandomPersonToUser();

        assertNotNull(user);
        assertNotNull(user.getEmail());
        assertNotNull(user.getId());

        Member member = user.getMember();
        promotePersonToUser(member);
        */
    }

    private Member getRandomPerson() {
        List<Member> people = Lists.newArrayList(memberRepository.findAll());
        Collections.shuffle(people, random);
        while(people.get(0).getId() == 133) { // one member in the default data is already a user.
            Collections.shuffle(people, random);
        }
        return people.get(0);
    }

    private UserDto createUserDto(Member member) {
        // TODO: Rework test as default is user<-> member 1:1
        return null; // createUserDto(member.getEmail());
    }

    private UserDto createUserDto(String email) {
        // TODO: Rework test as default is user<-> member 1:1
        final UserDto userDto = new UserDto();
        userDto.setEmail(email);
        userDto.setPassword("SecretPassword");
        userDto.setMatchingPassword("SecretPassword");
        userDto.setRole(0);
        return userDto;
    }
}
