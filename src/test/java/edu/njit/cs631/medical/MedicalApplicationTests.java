package edu.njit.cs631.medical;

import com.google.common.collect.Iterables;
import edu.njit.cs631.medical.data.entity.Address;
import edu.njit.cs631.medical.data.entity.Person;
import edu.njit.cs631.medical.data.entity.security.User;
import edu.njit.cs631.medical.data.repository.PersonCrudRepository;
import edu.njit.cs631.medical.data.repository.security.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletContext;
import javax.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest // (classes = {TestIntegrationConfig.class})
@Transactional
public class MedicalApplicationTests {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PersonCrudRepository personCrudRepository;

    @Autowired
    private PasswordEncoder encoder;

    @PersistenceContext
    private EntityManager entityManager;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

    }

	@Test
    @Sql(scripts = {"classpath:/truncate_all.sql", "classpath:/data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	public void contextLoadsWithNewUser() {
        ServletContext servletContext = wac.getServletContext();

        Assert.assertNotNull(servletContext);
        Assert.assertTrue(servletContext instanceof MockServletContext);
        Assert.assertNotNull(wac.getBean("userDetailsService"));
        userRepository.findAll().forEach(System.out::println);

        // Find the first person and promote them to a user without the
        Person personPromotion = Iterables.getFirst(personCrudRepository.findAll(), null); // the super special nelson brown id

        Assert.assertNotNull(personPromotion);
        System.out.println(String.format("Person to promote: %s %s", personPromotion.getId(), personPromotion.getFirstName()));

        User associatedUser = new User();
        associatedUser.setPerson(personPromotion);
        associatedUser.setPasswordHash(encoder.encode("somepassword"));
        associatedUser.setEnabled(true);
        associatedUser.setTokenExpired(false);

        entityManager.persist(associatedUser);

        entityManager.flush();
        entityManager.clear();

        Assert.assertNotNull(associatedUser.getId());
        Assert.assertTrue(associatedUser.getId() >= 200);

        // Create a new person
        Person newPerson = new Person();
        newPerson.setFirstName("Hoobity");
        newPerson.setLastName("Doobity");
        Address theirAddress = new Address();
        theirAddress.setAddress1("Hoobity Doobity Street");
        theirAddress.setCity("Doobityville");
        theirAddress.setPostalCode("08405");
        theirAddress.setState("NJ");
        theirAddress.setCounty("Atlantic");

        newPerson.setAddress(theirAddress);
        newPerson.setSsn("123-45-0000");
        newPerson.setHomePhone("(123) 123-1234");
        newPerson.setMobilePhone("(123) 123-0000");
        newPerson.setGender(Person.Gender.M);
        newPerson.setEmail("newPerson@thecoolhoobity.com");

        entityManager.persist(theirAddress);
        entityManager.persist(newPerson);
        entityManager.flush();

        Assert.assertTrue(newPerson.getId() >= 201);
        Assert.assertTrue(theirAddress.getId() >= 201);

	}

}
