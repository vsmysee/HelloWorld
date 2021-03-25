package com.example.helloworld;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class RandomHelper {

    private Set<Integer> sets = new HashSet<>();

    public Integer getIndex(int max) {
        int i = new Random().nextInt(max);
        if (!sets.contains(i)) {
            sets.add(i);
            return i;
        } else {
            return getIndex(max);
        }
    }
}
