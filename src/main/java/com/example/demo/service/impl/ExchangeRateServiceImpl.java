package com.example.demo.service.impl;

import com.example.demo.dto.ExchangeRateDto;
import com.example.demo.entity.ExchangeRate;
import com.example.demo.repository.ExchangeRateRepository;
import com.example.demo.service.ExchangeRateService;
import com.example.demo.util.CBUtil;
import com.example.demo.util.ExchangeRateComparator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class ExchangeRateServiceImpl implements ExchangeRateService {

    private final ExchangeRateRepository exchangeRateRepository;

    @Autowired
    public ExchangeRateServiceImpl(ExchangeRateRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
    }

    @Override
    public List<ExchangeRateDto> findCurrentRate() {
        Date currentDate = new Date();
        List<ExchangeRateDto> currentRate = exchangeRateRepository.findAllByDate(currentDate)
                .stream()
                .map(it -> ExchangeRateDto.valueOf(it))
                .collect(Collectors.toList());

        if (currentRate.size() == 0) {
            log.debug("В БД отсутствует список обменных курсов на текущую дату: " + currentDate);

            // Получить список из ЦБ
            List<ExchangeRate> listFromCB = CBUtil.getCurrentRate(currentDate);

            // Добавить в список российский рубль
            ExchangeRate rusRUB = new ExchangeRate();
            rusRUB.setDate(currentDate);
            rusRUB.setCharCode("RUR");
            rusRUB.setNominal(1);
            rusRUB.setName("Российский рубль");
            rusRUB.setValue(BigDecimal.ONE);
            List<ExchangeRate> listWithRUB = new ArrayList<>(listFromCB);
            listWithRUB.add(rusRUB);
            log.debug("Список с российским рублём: " + listWithRUB);

            // Отсортировать список по названиям валют
            List<ExchangeRate> finalList = listWithRUB.stream()
                    .sorted(new ExchangeRateComparator())
                    .collect(Collectors.toList());
            log.debug("Итоговый отсортированный список для сохранения: " + finalList);

            // Сохранить список в БД
            exchangeRateRepository.saveAll(finalList);
            log.debug("В БД сохранён список: " + finalList);

            // Преобразовать в DTO
            currentRate = finalList.stream()
                    .map(it -> ExchangeRateDto.valueOf(it))
                    .collect(Collectors.toList());
        }

        log.debug("currentRate: " + currentRate);
        return currentRate;
    }
}
