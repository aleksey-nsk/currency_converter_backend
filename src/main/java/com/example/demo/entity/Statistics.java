package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "statistics")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Statistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "from_valute")
    private String fromValute;

    @Column(name = "to_valute")
    private String toValute;

    @Column(name = "amount_convert")
    private Integer amountConvert;

    @Column(name = "average_rate")
    private BigDecimal averageRate;

    @Column(name = "sum_convert")
    private BigDecimal sumConvert;
}
