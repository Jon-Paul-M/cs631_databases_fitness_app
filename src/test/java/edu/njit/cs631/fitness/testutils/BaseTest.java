package edu.njit.cs631.fitness.testutils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import edu.njit.cs631.fitness.data.entity.security.User;
import edu.njit.cs631.fitness.data.repository.MembershipRepository;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import edu.njit.cs631.fitness.data.repository.MemberRepository;
import edu.njit.cs631.fitness.data.repository.security.UserRepository;
import edu.njit.cs631.fitness.service.api.UserService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest // (classes = {TestIntegrationConfig.class})
@Transactional
@TestPropertySource(locations="classpath:test.properties")
public abstract class BaseTest {


    @Autowired
    protected WebApplicationContext wac;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected MemberRepository memberRepository;

    @Autowired
    protected MembershipRepository membershipRepository;

    @Autowired
    protected PasswordEncoder encoder;

    @Autowired
    protected UserService userService;

    @PersistenceContext
    protected EntityManager entityManager;

    protected MockMvc mockMvc;

	@Autowired
	protected JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Bean
    public UserService userService() {
        return userService;
    }


    public User getAdminUser() {
        return userService.findUserByEmail("admin@test.com");
    }

    public User getMember() {
        return userService.findUserByEmail("member@test.com");
    }

    public User getHourlyInstructor() {
        return userService.findUserByEmail("hourlyinstructor@test.com");
    }

    public User getSalariedInstructor() {
        return userService.findUserByEmail("salariedinstructor@test.com");
    }

    public void loginAs(User user) throws Exception {
        assert (user != null);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/home")
                .accept(MediaType.TEXT_HTML)
                .param("email", user.getEmail())
                .param("password", "password"))
                .andExpect(model().errorCount(0))
                .andExpect(status().isOk());
    }

    public void loginAs(String email) throws Exception {
        loginAs(userService.findUserByEmail(email));
    }

    public void logout() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/home")
                .accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk());
    }

}
