package com.example.demo.util;

import com.example.demo.entity.ExchangeRate;

import java.util.Comparator;

public class ExchangeRateComparator implements Comparator<ExchangeRate> {

    @Override
    public int compare(ExchangeRate o1, ExchangeRate o2) {
        return o1.getName().compareTo(o2.getName());
    }
}
