package com.example.demo.unit.controller;

import com.example.demo.dto.ConvertDto;
import com.example.demo.exception.ExchangeRateNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@Log4j2
public class ConvertControllerUnitTest extends BaseUnitTestForController {

    @Test
    @DisplayName("Конвертация не добавлена (не найден обменный курс)")
    public void saveFail() throws Exception {
        ConvertDto convertDto = ConvertDto.valueOf(createConvert());

        String convertDtoAsJson = objectMapper.writeValueAsString(convertDto);
        log.debug("convertDtoAsJson: " + convertDtoAsJson);

        Mockito.doThrow(new ExchangeRateNotFoundException(convertDto.getFromValute(), new Date()))
                .when(convertService).save(convertDto);

        mockMvc.perform(post(BASE_CONVERT_URL).content(convertDtoAsJson).contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
