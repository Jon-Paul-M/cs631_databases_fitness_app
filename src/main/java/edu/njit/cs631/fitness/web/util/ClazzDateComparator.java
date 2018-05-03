package edu.njit.cs631.fitness.web.util;

import edu.njit.cs631.fitness.data.entity.Clazz;

import java.util.Comparator;

public class ClazzDateComparator implements Comparator<Clazz> {
    @Override
    public int compare(Clazz o1, Clazz o2) {
        return o1.getStart().compareTo(o2.getStart());
    }
}
