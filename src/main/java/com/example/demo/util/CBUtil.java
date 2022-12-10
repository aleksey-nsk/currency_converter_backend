package com.example.demo.util;

import com.example.demo.entity.ExchangeRate;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Log4j2
public class CBUtil { // утилитный класс для обращения к сайту ЦБ

    public static List<ExchangeRate> getCurrentRate(Date date) {
        String url = "http://www.cbr.ru/scripts/XML_daily.asp";
        log.debug("Сделать запрос на сайт ЦБ за текущим обменным курсом");
        log.debug("  url: " + url);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ValCurs> response = restTemplate.getForEntity(url, ValCurs.class);
        log.debug("  response: " + response);

        ValCurs body = response.getBody();
        List<ValCurs.Valute> valutes = body.getValutes();
        log.debug("  valutes: " + valutes);

        List<ExchangeRate> currentRate = new ArrayList<>();
        for (ValCurs.Valute valute : valutes) {
            ExchangeRate rate = new ExchangeRate();
            rate.setDate(date);
            rate.setIdCB(valute.id);
            rate.setNumCode(valute.numCode);
            rate.setCharCode(valute.charCode);
            rate.setNominal(valute.nominal);
            rate.setName(valute.name);
            rate.setValue(new BigDecimal(valute.value.replaceAll(",", ".")));
            currentRate.add(rate);
        }

        log.debug("  список из ЦБ: " + currentRate);
        return currentRate;
    }
}
