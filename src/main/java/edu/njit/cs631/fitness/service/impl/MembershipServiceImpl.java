package edu.njit.cs631.fitness.service.impl;

import edu.njit.cs631.fitness.data.entity.Membership;
import edu.njit.cs631.fitness.data.repository.MembershipRepository;
import edu.njit.cs631.fitness.service.api.MembershipService;
import edu.njit.cs631.fitness.web.model.MembershipModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("membershipService")
public class MembershipServiceImpl implements MembershipService {

    @Autowired
    MembershipRepository membershipRepository;

    @Override
    public void deleteMembership(Integer id) {

    }

    @Override
    public void addNewMembership(MembershipModel model) {

        Membership membership = membershipRepository.findOneByMembershipType(model.getMembershipName());

        if(membership != null) return; // maybe throw exception

        membership = new Membership();

        membership.setMembershipType(model.getMembershipName());
        membership.setAdvantages(model.getMembershipAdvantages());
        membership.setFee(model.getDecimalCost());

        membershipRepository.saveAndFlush(membership);


    }
}
