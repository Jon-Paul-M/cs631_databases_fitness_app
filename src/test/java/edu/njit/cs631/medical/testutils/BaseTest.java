package edu.njit.cs631.medical.testutils;

import edu.njit.cs631.medical.data.repository.PersonCrudRepository;
import edu.njit.cs631.medical.data.repository.security.UserRepository;
import edu.njit.cs631.medical.service.api.IUserService;
import edu.njit.cs631.medical.service.impl.UserService;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest // (classes = {TestIntegrationConfig.class})
@Transactional
public abstract class BaseTest {


    @Autowired
    protected WebApplicationContext wac;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected PersonCrudRepository personCrudRepository;

    @Autowired
    protected PasswordEncoder encoder;

    @Autowired
    protected IUserService userService;

    @PersistenceContext
    protected EntityManager entityManager;

    protected MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Bean
    public IUserService userService() {
        return userService;
    }

}
