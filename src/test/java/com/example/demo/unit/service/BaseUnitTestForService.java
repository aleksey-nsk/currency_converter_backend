package com.example.demo.unit.service;

import com.example.demo.entity.Convert;
import com.example.demo.entity.ExchangeRate;
import com.example.demo.entity.Statistics;
import com.example.demo.repository.ConvertRepository;
import com.example.demo.repository.ExchangeRateRepository;
import com.example.demo.repository.StatisticsRepository;
import com.example.demo.service.ConvertService;
import com.example.demo.service.ExchangeRateService;
import com.example.demo.service.StatisticsService;
import com.example.demo.service.impl.ConvertServiceImpl;
import com.example.demo.service.impl.ExchangeRateServiceImpl;
import com.example.demo.service.impl.StatisticsServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Date;

@SpringBootTest(classes = {ConvertServiceImpl.class, ExchangeRateServiceImpl.class, StatisticsServiceImpl.class})
@ActiveProfiles("test")
@Log4j2
public abstract class BaseUnitTestForService {

    @Autowired
    protected ConvertService convertService;

    @Autowired
    protected ExchangeRateService exchangeRateService;

    @Autowired
    protected StatisticsService statisticsService;

    @MockBean
    protected ConvertRepository convertRepository;

    @MockBean
    protected ExchangeRateRepository exchangeRateRepository;

    @MockBean
    protected StatisticsRepository statisticsRepository;

    protected Convert createConvert() {
        String fromValute = "Валюта_" + RandomStringUtils.randomAlphabetic(10);
        BigDecimal amount = new BigDecimal(RandomStringUtils.randomNumeric(4));
        String toValute = "Валюта_" + RandomStringUtils.randomAlphabetic(10);

        Convert convert = new Convert();
        convert.setFromValute(fromValute);
        convert.setAmount(amount);
        convert.setToValute(toValute);

        log.debug("convert: " + convert);
        return convert;
    }

    protected ExchangeRate createRate(Long id) {
        Date currentDate = new Date();
        String idCB = RandomStringUtils.randomAlphanumeric(7).toUpperCase();
        String numCode = RandomStringUtils.randomNumeric(3);
        String charCode = RandomStringUtils.randomAlphabetic(3).toUpperCase();
        Integer nominal = 100;
        String name = "Валюта_" + RandomStringUtils.randomAlphabetic(10);
        BigDecimal value = new BigDecimal(RandomStringUtils.randomNumeric(3));

        ExchangeRate rate = new ExchangeRate(id, currentDate, idCB, numCode, charCode, nominal, name, value);
        log.debug("rate: " + rate);

        return rate;
    }

    protected Statistics createStatistics(Long id) {
        String fromValute = "Валюта_" + RandomStringUtils.randomAlphabetic(10);
        String toValute = "Валюта_" + RandomStringUtils.randomAlphabetic(10);
        Integer amountConvert = 1;
        BigDecimal averageRate = new BigDecimal(RandomStringUtils.randomNumeric(3));
        BigDecimal sumConvert = new BigDecimal(RandomStringUtils.randomNumeric(4));

        Statistics statistics = new Statistics();
        statistics.setId(id);
        statistics.setFromValute(fromValute);
        statistics.setToValute(toValute);
        statistics.setAmountConvert(amountConvert);
        statistics.setAverageRate(averageRate);
        statistics.setSumConvert(sumConvert);

        log.debug("statistics: " + statistics);
        return statistics;
    }
}
