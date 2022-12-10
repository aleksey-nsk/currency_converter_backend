package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "convert")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Convert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date")
    private Date date;

    @Column(name = "from_valute")
    private String fromValute;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "to_valute")
    private String toValute;

    @Column(name = "rate")
    private BigDecimal rate;

    @Column(name = "result")
    private BigDecimal result;

    /**
     * <strong>Copy Constructor</strong>
     *
     * <p>Long - immutable type</p>
     * <p>String - immutable type</p>
     * <p>BigDecimal - immutable type</p>
     *
     * <p>Date - mutable</p>
     *
     * @param convert Объект для копирования
     */
    public Convert(Convert convert) {
        this.id = convert.getId();
        this.date = new Date(convert.getDate().getTime());
        this.fromValute = convert.getFromValute();
        this.amount = convert.getAmount();
        this.toValute = convert.getToValute();
        this.rate = convert.getRate();
        this.result = convert.getResult();
    }
}
