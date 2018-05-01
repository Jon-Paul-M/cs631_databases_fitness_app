package edu.njit.cs631.fitness.usecases.mvp.unauthenticated_user;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;

import edu.njit.cs631.fitness.data.entity.Membership;
import edu.njit.cs631.fitness.data.repository.MembershipRepository;
import edu.njit.cs631.fitness.testutils.BaseTest;

@Sql(scripts = {"classpath:/truncate_all.sql", "classpath:/data-default.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class T_001_ViewMemberships_Test extends BaseTest {

    @Autowired
    private MembershipRepository membershipRepository;

    @Test
    public void findMemberships_MembershipsExist_RepoReturnsMemberships() {
        int rowCount = JdbcTestUtils.countRowsInTable(jdbcTemplate, Membership.TABLE_NAME);
        List<Membership> memberships = (List<Membership>) membershipRepository.findAll();
        Assert.assertNotNull("memberships should never be null", memberships);
        Assert.assertNotEquals("memberships should not be empty", 0, memberships.size());
        Assert.assertEquals("number of rows should should equal number of entities", rowCount, memberships.size());
    }
}
