package edu.njit.cs631.fitness.web.util;

import edu.njit.cs631.fitness.data.entity.Member;

import java.util.Comparator;

public class MemberNameComparator implements Comparator<Member> {
    @Override
    public int compare(Member member, Member t1) {
        return member.getName().compareTo(t1.getName());
    }
}
