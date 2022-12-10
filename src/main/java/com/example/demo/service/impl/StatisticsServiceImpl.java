package com.example.demo.service.impl;

import com.example.demo.dto.StatisticsDto;
import com.example.demo.entity.Convert;
import com.example.demo.entity.Statistics;
import com.example.demo.repository.StatisticsRepository;
import com.example.demo.service.StatisticsService;
import com.example.demo.util.StatisticsDtoComparator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static java.math.BigDecimal.ROUND_HALF_UP;

@Service
@Log4j2
public class StatisticsServiceImpl implements StatisticsService {

    private final StatisticsRepository statisticsRepository;

    @Autowired
    public StatisticsServiceImpl(StatisticsRepository statisticsRepository) {
        this.statisticsRepository = statisticsRepository;
    }

    @Override
    public List<StatisticsDto> findAll() {
        List<StatisticsDto> list = statisticsRepository.findAll()
                .stream()
                .map(it -> StatisticsDto.valueOf(it))
                .sorted(new StatisticsDtoComparator())
                .collect(Collectors.toList());

        log.debug("Вся статистика: " + list);
        return list;
    }

    @Override
    @Transactional
    public void update(Convert convert) {
        log.debug("Обновить статистику");
        log.debug("  данные по новой конвертации: " + convert);

        String fromValute = convert.getFromValute();
        String toValute = convert.getToValute();
        BigDecimal rate = convert.getRate();
        BigDecimal result = convert.getResult();

        Statistics statisticsCurrent = statisticsRepository.findFirstByFromValuteAndToValute(fromValute, toValute);
        log.debug("  statisticsCurrent: " + statisticsCurrent);

        if (statisticsCurrent == null) {
            Statistics statistics = new Statistics();
            statistics.setFromValute(fromValute);
            statistics.setToValute(toValute);
            statistics.setAmountConvert(1);
            statistics.setAverageRate(rate);
            statistics.setSumConvert(result);
            log.debug("  сохранить новую запись: " + statistics);

            Statistics saved = statisticsRepository.save(statistics);
            log.debug("  в БД сохранена новая запись: " + saved);
            return;
        }

        Integer amountCurrent = statisticsCurrent.getAmountConvert();
        BigDecimal averageRateCurrent = statisticsCurrent.getAverageRate();
        BigDecimal sumCurrent = statisticsCurrent.getSumConvert();
        log.debug("  amountCurrent: " + amountCurrent);
        log.debug("  averageRateCurrent: " + averageRateCurrent);
        log.debug("  sumCurrent: " + sumCurrent);

        Integer amountNew = amountCurrent + 1;
        log.debug("  amountNew: " + amountNew);

        // Формула для пересчёта среднего курса: (amountCurrent * averageRateCurrent + rate) / amountNew
        BigDecimal averageRateNew = (BigDecimal.valueOf(amountCurrent).multiply(averageRateCurrent).add(rate)).divide(BigDecimal.valueOf(amountNew), 4, ROUND_HALF_UP);
        log.debug("  averageRateNew: " + averageRateNew);

        BigDecimal sumNew = sumCurrent.add(result);
        log.debug("  sumNew: " + sumNew);

        log.debug("  обновить текущую запись");
        statisticsRepository.updateStat(statisticsCurrent.getId(), amountNew, averageRateNew, sumNew);
    }
}
