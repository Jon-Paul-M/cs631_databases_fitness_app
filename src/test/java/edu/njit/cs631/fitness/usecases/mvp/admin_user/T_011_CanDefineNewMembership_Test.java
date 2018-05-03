package edu.njit.cs631.fitness.usecases.mvp.admin_user;

import edu.njit.cs631.fitness.data.entity.Membership;
import edu.njit.cs631.fitness.service.api.MembershipService;
import edu.njit.cs631.fitness.testutils.BaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class T_011_CanDefineNewMembership_Test extends BaseTest {

        // UnitOfWork_StateUnderTest_ExpectedBehavior

        @Autowired
        private MembershipService membershipService;

        @Test
        @Sql(scripts = {"classpath:/truncate_all.sql", "classpath:/data-h2.sql"},
                executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        public void adminGetExerciseCreationForm() throws Exception {
            mockMvc.perform(
                    MockMvcRequestBuilders
                            .get("/admin/memberships/create")
                            .accept(MediaType.TEXT_HTML)
                            .with(user(getAdminUser())))
                    .andExpect(status().isOk());
        }

        @Test
        @Sql(scripts = {"classpath:/truncate_all.sql", "classpath:/data-h2.sql"},
                executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        public void adminCreatesExercise() throws Exception {
            String dne = "DOESNOTEXIST";
            Membership membership = membershipRepository.findOneByMembershipType(dne);

            Assert.assertNull("No membership with name", membership);
            mockMvc.perform(
                    MockMvcRequestBuilders
                            .post("/admin/memberships/create")
                            .accept(MediaType.TEXT_HTML)
                            .param("membershipName", dne)
                            .param("membershipAdvantages", "bigly yuggeeee benefits")
                            .param("cost", "$1.23")
                            .with(user(getAdminUser())))
                    .andExpect(status().is3xxRedirection());
            membership = membershipRepository.findOneByMembershipType(dne);
            Assert.assertTrue("Build exists with a membership", membership.getMembershipType().equals(dne));
        }
}
