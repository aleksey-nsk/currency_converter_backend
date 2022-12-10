package com.example.demo.service.impl;

import com.example.demo.dto.ConvertDto;
import com.example.demo.entity.Convert;
import com.example.demo.entity.ExchangeRate;
import com.example.demo.exception.ExchangeRateNotFoundException;
import com.example.demo.repository.ConvertRepository;
import com.example.demo.repository.ExchangeRateRepository;
import com.example.demo.service.ConvertService;
import com.example.demo.service.StatisticsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static java.math.BigDecimal.ROUND_HALF_UP;

@Service
@Log4j2
public class ConvertServiceImpl implements ConvertService {

    private final ConvertRepository convertRepository;
    private final ExchangeRateRepository exchangeRateRepository;
    private final StatisticsService statisticsService;

    @Autowired
    public ConvertServiceImpl(ConvertRepository convertRepository, ExchangeRateRepository exchangeRateRepository, StatisticsService statisticsService) {
        this.convertRepository = convertRepository;
        this.exchangeRateRepository = exchangeRateRepository;
        this.statisticsService = statisticsService;
    }

    private final static int PAGE_SIZE = 4;

    @Override
    public Page<ConvertDto> findConvertPage(Integer pageIndex) {
        log.debug("Получить страницу с конвертациями");
        log.debug("  номер страницы: " + pageIndex);

        Pageable pageable = PageRequest.of(pageIndex - 1, PAGE_SIZE, Sort.by("id").descending());
        Page<ConvertDto> page = convertRepository.findAll(pageable).map(it -> ConvertDto.valueOf(it));
        List<ConvertDto> content = page.getContent();

        log.debug("  список конвертаций на искомой странице: " + content);
        log.debug("  всего конвертаций на странице: " + content.size());
        return page;
    }

    @Override
    public ConvertDto save(ConvertDto convertDto) {
        log.debug("Произвести новую конвертацию и сохранить её в БД");
        log.debug("  convertDto: " + convertDto);

        Convert convert = convertDto.mapToConvert();
        String fromValute = convert.getFromValute();
        String toValute = convert.getToValute();
        Date currentDate = new Date();
        ExchangeRate from = exchangeRateRepository.findFirstByNameAndDate(fromValute, currentDate).orElseThrow(() -> new ExchangeRateNotFoundException(fromValute, currentDate));
        ExchangeRate to = exchangeRateRepository.findFirstByNameAndDate(toValute, currentDate).orElseThrow(() -> new ExchangeRateNotFoundException(toValute, currentDate));
        log.debug("  from: " + from);
        log.debug("  to: " + to);

        // Формула курса: (nominalTo * valueFrom) / (nominalFrom * valueTo)
        Integer nominalFrom = from.getNominal();
        BigDecimal valueFrom = from.getValue();
        Integer nominalTo = to.getNominal();
        BigDecimal valueTo = to.getValue();
        BigDecimal rate = (BigDecimal.valueOf(nominalTo).multiply(valueFrom)).divide(BigDecimal.valueOf(nominalFrom).multiply(valueTo), 4, ROUND_HALF_UP);
        log.debug("  курс: " + rate);

        BigDecimal result = convert.getAmount().multiply(rate);
        log.debug("  результат: " + result);

        convert.setDate(currentDate);
        convert.setRate(rate);
        convert.setResult(result);
        log.debug("  конвертация для сохранения в БД: " + convert);

        Convert saved = convertRepository.save(convert);
        log.debug("  saved: " + saved);

        // Используем Copy Constructor
        Convert copy = new Convert(saved);
        statisticsService.update(copy);

        return ConvertDto.valueOf(saved);
    }
}
