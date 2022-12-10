package com.example.demo.unit.controller;

import com.example.demo.controller.ConvertController;
import com.example.demo.controller.ExchangeRateController;
import com.example.demo.controller.StatisticsController;
import com.example.demo.entity.Convert;
import com.example.demo.entity.ExchangeRate;
import com.example.demo.entity.Statistics;
import com.example.demo.service.ConvertService;
import com.example.demo.service.ExchangeRateService;
import com.example.demo.service.StatisticsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Date;

@WebMvcTest(controllers = {ConvertController.class, ExchangeRateController.class, StatisticsController.class})
@ActiveProfiles("test")
@Log4j2
public abstract class BaseUnitTestForController {

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    protected ConvertService convertService;

    @MockBean
    protected ExchangeRateService exchangeRateService;

    @MockBean
    protected StatisticsService statisticsService;

    @Autowired
    protected ObjectMapper objectMapper;

    protected static final String BASE_CONVERT_URL = "/api/v1/convert";
    protected static final String BASE_RATE_URL = "/api/v1/rate";
    protected static final String BASE_STATISTICS_URL = "/api/v1/statistics";

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
