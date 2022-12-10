package com.example.demo.integration;

import com.example.demo.dto.ExchangeRateDto;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@Log4j2
public class ExchangeRateControllerIntegrationTest extends BaseIntegrationTest {

    @Test
    @DisplayName("Успешный поиск всех обменных курсов на текущую дату")
    public void findCurrentRateSuccess() throws Exception {
        Date currentDate = new Date();
        Date yesterdayDate = new Date(currentDate.getTime() - MILLISECONDS_IN_DAY);
        log.debug("currentDate: " + currentDate);
        log.debug("yesterdayDate: " + yesterdayDate);

        ExchangeRateDto yesterday1 = ExchangeRateDto.valueOf(saveRateInDB(yesterdayDate));
        ExchangeRateDto yesterday2 = ExchangeRateDto.valueOf(saveRateInDB(yesterdayDate));

        assertThat(exchangeRateRepository.findAll().size()).isEqualTo(2);

        mockMvc.perform(get(BASE_RATE_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").isNumber())
                .andExpect(jsonPath("$.[0].date").isNotEmpty())
                .andExpect(jsonPath("$.[0].idCB").isNotEmpty())
                .andExpect(jsonPath("$.[0].numCode").isNotEmpty())
                .andExpect(jsonPath("$.[0].charCode").isNotEmpty())
                .andExpect(jsonPath("$.[0].nominal").isNumber())
                .andExpect(jsonPath("$.[0].name").isString())
                .andExpect(jsonPath("$.[0].value").isNumber());

        // 34 пришло из ЦБ + российский рубль + 2 вчерашних курса = 37
        assertThat(exchangeRateRepository.findAll().size()).isEqualTo(37);
    }
}
