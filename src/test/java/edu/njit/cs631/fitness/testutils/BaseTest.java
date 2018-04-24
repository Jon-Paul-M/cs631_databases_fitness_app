package edu.njit.cs631.fitness.testutils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import edu.njit.cs631.fitness.data.repository.MemberCrudRepository;
import edu.njit.cs631.fitness.data.repository.security.UserRepository;
import edu.njit.cs631.fitness.service.api.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest // (classes = {TestIntegrationConfig.class})
@Transactional
public abstract class BaseTest {


    @Autowired
    protected WebApplicationContext wac;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected MemberCrudRepository memberCrudRepository;

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

}
