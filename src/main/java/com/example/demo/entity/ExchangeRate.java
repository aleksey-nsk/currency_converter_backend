package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "exchange_rate")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRate { // обменный курс по отношению к рублю

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date")
    private Date date;

    @Column(name = "id_cb")
    private String idCB; // идентификатор ЦБ

    @Column(name = "num_code")
    private String numCode;

    @Column(name = "char_code")
    private String charCode;

    @Column(name = "nominal")
    private Integer nominal;

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private BigDecimal value; // всегда использовать BigDecimal для работы с деньгами
}
