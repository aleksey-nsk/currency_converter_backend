package com.example.demo.util;

import com.example.demo.dto.StatisticsDto;

import java.util.Comparator;

public class StatisticsDtoComparator implements Comparator<StatisticsDto> {

    @Override
    public int compare(StatisticsDto o1, StatisticsDto o2) {
        return o2.getId().compareTo(o1.getId());
    }
}
