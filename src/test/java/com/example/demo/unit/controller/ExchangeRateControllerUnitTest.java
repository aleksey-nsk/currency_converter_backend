package com.example.demo.unit.controller;

import com.example.demo.dto.ExchangeRateDto;
import com.example.demo.entity.ExchangeRate;
import com.example.demo.util.ExchangeRateComparator;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@Log4j2
public class ExchangeRateControllerUnitTest extends BaseUnitTestForController {

    @Test
    @DisplayName("Успешный поиск всех обменных курсов на текущую дату")
    public void findCurrentRateSuccess() throws Exception {
        ExchangeRate created1 = createRate(1L);
        ExchangeRate created2 = createRate(2L);

        List<ExchangeRate> list = new ArrayList<>();
        list.add(created1);
        list.add(created2);
        log.debug("list: " + list);

        List<ExchangeRateDto> sortedList = list.stream()
                .sorted(new ExchangeRateComparator())
                .map(it -> ExchangeRateDto.valueOf(it))
                .collect(Collectors.toList());
        log.debug("sortedList: " + sortedList);

        String expectedJson = objectMapper.writeValueAsString(sortedList);
        log.debug("expectedJson: " + expectedJson);

        Mockito.doReturn(sortedList)
                .when(exchangeRateService).findCurrentRate();

        mockMvc.perform(get(BASE_RATE_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson, true));
    }
}
