package com.example.demo.integration;

import com.example.demo.entity.Convert;
import com.example.demo.entity.ExchangeRate;
import com.example.demo.entity.Statistics;
import com.example.demo.repository.ConvertRepository;
import com.example.demo.repository.ExchangeRateRepository;
import com.example.demo.repository.StatisticsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Date;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Log4j2
public abstract class BaseIntegrationTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected ConvertRepository convertRepository;

    @Autowired
    protected ExchangeRateRepository exchangeRateRepository;

    @Autowired
    protected StatisticsRepository statisticsRepository;

    protected static final String BASE_CONVERT_URL = "/api/v1/convert";
    protected static final String BASE_RATE_URL = "/api/v1/rate";
    protected static final String BASE_STATISTICS_URL = "/api/v1/statistics";
    protected static final long MILLISECONDS_IN_DAY = 24 * 60 * 60 * 1_000;

    @AfterEach
    void tearDown() {
        convertRepository.deleteAll();
        exchangeRateRepository.deleteAll();
        statisticsRepository.deleteAll();
    }

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

    protected Convert saveConvertInDB() {
        Date currentDate = new Date();
        String fromValute = "Валюта_" + RandomStringUtils.randomAlphabetic(10);
        BigDecimal amount = new BigDecimal(RandomStringUtils.randomNumeric(4));
        String toValute = "Валюта_" + RandomStringUtils.randomAlphabetic(10);
        BigDecimal rate = new BigDecimal(RandomStringUtils.randomNumeric(3));
        BigDecimal result = amount.multiply(rate);

        Convert convert = new Convert();
        convert.setDate(currentDate);
        convert.setFromValute(fromValute);
        convert.setAmount(amount);
        convert.setToValute(toValute);
        convert.setRate(rate);
        convert.setResult(result);
        log.debug("convert: " + convert);

        Convert savedConvert = convertRepository.save(convert);
        log.debug("savedConvert: " + savedConvert);
        return savedConvert;
    }

    protected ExchangeRate saveRateInDB(Date date) {
        String idCB = RandomStringUtils.randomAlphanumeric(7).toUpperCase();
        String numCode = RandomStringUtils.randomNumeric(3);
        String charCode = RandomStringUtils.randomAlphabetic(3).toUpperCase();
        Integer nominal = 100;
        String name = "Валюта_" + RandomStringUtils.randomAlphabetic(10);
        BigDecimal value = new BigDecimal(RandomStringUtils.randomNumeric(3));

        ExchangeRate rate = new ExchangeRate();
        rate.setDate(date);
        rate.setIdCB(idCB);
        rate.setNumCode(numCode);
        rate.setCharCode(charCode);
        rate.setNominal(nominal);
        rate.setName(name);
        rate.setValue(value);
        log.debug("rate: " + rate);

        ExchangeRate savedRate = exchangeRateRepository.save(rate);
        log.debug("savedRate: " + savedRate);
        return savedRate;
    }

    protected Statistics saveStatisticsInDB() {
        String fromValute = "Валюта_" + RandomStringUtils.randomAlphabetic(10);
        String toValute = "Валюта_" + RandomStringUtils.randomAlphabetic(10);
        Integer amountConvert = 1;
        BigDecimal averageRate = new BigDecimal(RandomStringUtils.randomNumeric(3));
        BigDecimal sumConvert = new BigDecimal(RandomStringUtils.randomNumeric(4));

        Statistics statistics = new Statistics();
        statistics.setFromValute(fromValute);
        statistics.setToValute(toValute);
        statistics.setAmountConvert(amountConvert);
        statistics.setAverageRate(averageRate);
        statistics.setSumConvert(sumConvert);
        log.debug("statistics: " + statistics);

        Statistics savedStatistics = statisticsRepository.save(statistics);
        log.debug("savedStatistics: " + savedStatistics);
        return savedStatistics;
    }
}
