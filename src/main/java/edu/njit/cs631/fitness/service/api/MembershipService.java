package edu.njit.cs631.fitness.service.api;

import edu.njit.cs631.fitness.web.model.MembershipModel;

public interface MembershipService {

    void deleteMembership(Integer id);
    void addNewMembership(MembershipModel model);
}
