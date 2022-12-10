package com.example.demo.service;

import com.example.demo.dto.StatisticsDto;
import com.example.demo.entity.Convert;

import java.util.List;

/**
 * @author Aleksey Zhdanov
 * @version 1
 */
public interface StatisticsService {

    /**
     * <p>Возвращает всю статистику</p>
     *
     * @return Вся статистика
     */
    List<StatisticsDto> findAll();

    /**
     * <p>Обновляет статистику</p>
     *
     * @param convert Данные по новой конвертации
     */
    void update(Convert convert);
}
