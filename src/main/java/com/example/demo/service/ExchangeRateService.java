package com.example.demo.service;

import com.example.demo.dto.ExchangeRateDto;

import java.util.List;

/**
 * @author Aleksey Zhdanov
 * @version 1
 */
public interface ExchangeRateService {

    /**
     * <p>Возвращает список обменных курсов на текущую дату</p>
     *
     * @return Список обменных курсов
     */
    List<ExchangeRateDto> findCurrentRate();
}
