package edu.njit.cs631.fitness;

import com.google.common.collect.Iterables;
import edu.njit.cs631.fitness.data.entity.Member;
import edu.njit.cs631.fitness.data.entity.security.User;
import edu.njit.cs631.fitness.testutils.BaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.jdbc.Sql;
import javax.servlet.ServletContext;

public class ApplicationTests extends BaseTest {

	@Test
    @Sql(scripts = {"classpath:/truncate_all.sql", "classpath:/data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	public void contextLoadsWithNewUser() {
        ServletContext servletContext = wac.getServletContext();

        Assert.assertNotNull(servletContext);
        Assert.assertTrue(servletContext instanceof MockServletContext);
        Assert.assertNotNull(wac.getBean("userDetailsService"));
        userRepository.findAll().forEach(System.out::println);

        // Find the first member and promote them to a user without the
        // TODO: Rework for no member promotion, user the superclass
        /*
        Member memberPromotion = Iterables.getFirst(memberCrudRepository.findAll(), null); // the super special nelson brown id

        Assert.assertNotNull(memberPromotion);
        System.out.println(String.format("Member as user: %s %s", memberPromotion.getId()));
        */

        User associatedUser = new User();
        associatedUser.setName("Hoobity Doobity");
        associatedUser.setEmail("newMember@thecoolhoobity.com");
        associatedUser.setPasswordHash(encoder.encode("somepassword"));
        associatedUser.setEnabled(true);
        associatedUser.setTokenExpired(false);

        entityManager.persist(associatedUser);

        entityManager.flush();
        entityManager.clear();

        Assert.assertNotNull(associatedUser.getId());
        Assert.assertTrue(associatedUser.getId() >= 200);

        // Create a new member
        Member newMember = new Member();
        newMember.setName("The New Member");
        newMember.setEmail("newMember@thecoolhoobity.com");
        newMember.setPasswordHash(encoder.encode("somepassword"));
        newMember.setAddress1("Hoobity Doobity Street");
        newMember.setCity("Doobityville");
        newMember.setPostalCode("08405");
        newMember.setState("NJ");
        newMember.setCounty("Atlantic");

        entityManager.persist(newMember);
        entityManager.flush();

        Assert.assertTrue(newMember.getId() >= 201);

	}

}
