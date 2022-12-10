package com.example.demo.unit.service;

import com.example.demo.dto.ConvertDto;
import com.example.demo.exception.ExchangeRateNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@Log4j2
public class ConvertServiceUnitTest extends BaseUnitTestForService {

    @Test
    @DisplayName("Конвертация не добавлена (не найден обменный курс)")
    public void saveFail() {
        ConvertDto convertDto = ConvertDto.valueOf(createConvert());

        Mockito.doReturn(Optional.empty())
                .when(exchangeRateRepository).findFirstByNameAndDate(Mockito.any(), Mockito.any());

        try {
            convertService.save(convertDto);
            throw new RuntimeException("Ожидаемое исключение не было выброшено");
        } catch (ExchangeRateNotFoundException e) {
            log.debug(e.getMessage());
            assertThat(e.getMessage()).contains(convertDto.getFromValute());
        }
    }
}
