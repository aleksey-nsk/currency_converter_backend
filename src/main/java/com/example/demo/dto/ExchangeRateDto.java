package com.example.demo.dto;

import com.example.demo.entity.ExchangeRate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRateDto {

    private Long id;
    private Date date;
    private String idCB;
    private String numCode;
    private String charCode;
    private Integer nominal;
    private String name;
    private BigDecimal value;

    public static ExchangeRateDto valueOf(ExchangeRate exchangeRate) {
        return new ExchangeRateDto(
                exchangeRate.getId(),
                exchangeRate.getDate(),
                exchangeRate.getIdCB(),
                exchangeRate.getNumCode(),
                exchangeRate.getCharCode(),
                exchangeRate.getNominal(),
                exchangeRate.getName(),
                exchangeRate.getValue()
        );
    }

    public ExchangeRate mapToExchangeRate() {
        return new ExchangeRate(id, date, idCB, numCode, charCode, nominal, name, value);
    }
}
