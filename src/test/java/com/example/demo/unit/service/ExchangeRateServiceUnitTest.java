package com.example.demo.unit.service;

import com.example.demo.dto.ExchangeRateDto;
import com.example.demo.entity.ExchangeRate;
import com.example.demo.util.ExchangeRateComparator;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@Log4j2
public class ExchangeRateServiceUnitTest extends BaseUnitTestForService {

    @Test
    @DisplayName("Успешный поиск обменных курсов (данные берутся из базы)")
    public void findCurrentRateFromDBSuccess() {
        ExchangeRate created1 = createRate(1L);
        ExchangeRate created2 = createRate(2L);
        ExchangeRate created3 = createRate(3L);

        List<ExchangeRate> list = new ArrayList<>();
        list.add(created1);
        list.add(created2);
        list.add(created3);
        log.debug("list: " + list);

        List<ExchangeRate> sortedList = list.stream()
                .sorted(new ExchangeRateComparator())
                .collect(Collectors.toList());
        log.debug("sortedList: " + sortedList);

        List<ExchangeRateDto> sortedDtoList = sortedList.stream()
                .map(it -> ExchangeRateDto.valueOf(it))
                .collect(Collectors.toList());
        log.debug("sortedDtoList: " + sortedDtoList);

        Mockito.doReturn(sortedList)
                .when(exchangeRateRepository).findAllByDate(Mockito.any());

        List<ExchangeRateDto> actual = exchangeRateService.findCurrentRate();
        log.debug("actual: " + actual);

        assertThat(actual).size().isEqualTo(3);
        assertThat(actual).isEqualTo(sortedDtoList);
    }

    @Test
    @DisplayName("Успешный поиск обменных курсов (данные берутся с сайта ЦБ)")
    public void findCurrentRateFromCBSuccess() {
        Mockito.doReturn(Collections.emptyList())
                .when(exchangeRateRepository).findAllByDate(Mockito.any());

        List<ExchangeRateDto> actual = exchangeRateService.findCurrentRate();
        log.debug("actual: " + actual);

        // 34 пришло из ЦБ + российский рубль = 35
        assertThat(actual).size().isEqualTo(35);
    }
}
