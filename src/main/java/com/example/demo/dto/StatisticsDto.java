package com.example.demo.dto;

import com.example.demo.entity.Statistics;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsDto {

    private Long id;
    private String fromValute;
    private String toValute;
    private Integer amountConvert;
    private BigDecimal averageRate;
    private BigDecimal sumConvert;

    public static StatisticsDto valueOf(Statistics statistics) {
        return new StatisticsDto(
                statistics.getId(),
                statistics.getFromValute(),
                statistics.getToValute(),
                statistics.getAmountConvert(),
                statistics.getAverageRate(),
                statistics.getSumConvert()
        );
    }

    public Statistics mapToStatistics() {
        return new Statistics(id, fromValute, toValute, amountConvert, averageRate, sumConvert);
    }
}
