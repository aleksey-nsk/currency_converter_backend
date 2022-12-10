package com.example.demo.exception;

import java.util.Date;

public class ExchangeRateNotFoundException extends RuntimeException {

    public ExchangeRateNotFoundException(String name, Date date) {
        super("Не найден обменный курс с именем '" + name + "' и датой '" + date + "'");
    }
}
