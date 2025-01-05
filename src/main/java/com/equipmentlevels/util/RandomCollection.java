package com.chongyu.aliveandwell.equipmentlevels.util;

import net.minecraft.util.math.random.Random;

import java.util.NavigableMap;
import java.util.TreeMap;

public class RandomCollection<E> {
    private final NavigableMap<Double, E> map = new TreeMap<Double, E>();
    private double total = 0;

    public void add(double weight, E result) {
        if (weight <= 0) return;
        total += weight;
        map.put(total, result);
    }

    public E next(Random random) {
        double value = random.nextDouble() * total;
        return map.ceilingEntry(value).getValue();
    }
}