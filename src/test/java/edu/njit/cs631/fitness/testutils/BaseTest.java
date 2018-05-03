package edu.njit.cs631.fitness.testutils;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import edu.njit.cs631.fitness.data.repository.MemberRepository;
import edu.njit.cs631.fitness.data.repository.MembershipRepository;
import edu.njit.cs631.fitness.data.repository.security.UserRepository;
import edu.njit.cs631.fitness.service.api.UserService;

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

    @Autowired
    private UserDetailsService userDetailsService;

    @PersistenceContext
    protected EntityManager entityManager;

    protected MockMvc mockMvc;

	@Autowired
	protected JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .apply(springSecurity())
                .build();
    }

    @Bean
    public UserService userService() {
        return userService;
    }


    protected UserDetails getAdminUser() {
        return userDetailsService.loadUserByUsername("admin@test.com");
    }

    protected UserDetails getMemberUser() {
        return userDetailsService.loadUserByUsername("member@test.com");
    }

    protected UserDetails getHourlyInstructor() {
        return userDetailsService.loadUserByUsername("hourlyinstructor@test.com");
    }

    protected UserDetails getSalariedInstructor() {
        return userDetailsService.loadUserByUsername("salariedinstructor@test.com");
    }

    protected UserDetails getInstructorMember() {
        return userDetailsService.loadUserByUsername("instructormember@test.com");
    }
}
