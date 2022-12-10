package com.example.demo.dto;

import com.example.demo.entity.Convert;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConvertDto {

    private Long id;
    private Date date;

    @NotNull
    private String fromValute;

    @NotNull
    @Min(0)
    @Max(1_000_000_000)
    private BigDecimal amount;

    @NotNull
    private String toValute;

    private BigDecimal rate;
    private BigDecimal result;

    public static ConvertDto valueOf(Convert convert) {
        return new ConvertDto(
                convert.getId(),
                convert.getDate(),
                convert.getFromValute(),
                convert.getAmount(),
                convert.getToValute(),
                convert.getRate(),
                convert.getResult()
        );
    }

    public Convert mapToConvert() {
        return new Convert(id, date, fromValute, amount, toValute, rate, result);
    }
}
