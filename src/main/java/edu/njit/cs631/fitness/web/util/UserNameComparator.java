package edu.njit.cs631.fitness.web.util;

import edu.njit.cs631.fitness.data.entity.security.User;

import java.util.Comparator;

public class UserNameComparator implements Comparator<User> {
    @Override
    public int compare(User member, User t1) {
        return member.getName().compareTo(t1.getName());
    }
}
