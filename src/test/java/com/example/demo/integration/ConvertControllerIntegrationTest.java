package com.example.demo.integration;

import com.example.demo.dto.ConvertDto;
import com.example.demo.entity.Convert;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@Log4j2
public class ConvertControllerIntegrationTest extends BaseIntegrationTest {

    @Test
    @DisplayName("Успешный поиск первой страницы с конвертациями")
    public void findFirstPageSuccess() throws Exception {
        Convert convert1 = saveConvertInDB();
        Convert convert2 = saveConvertInDB();

        assertThat(convertRepository.findAll().size()).isEqualTo(2);

        mockMvc.perform(get(BASE_CONVERT_URL + "?pageIndex=1"))
                .andDo(print())
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.content[0].id").value(convert2.getId()))
                .andExpect(jsonPath("$.content[0].date").isString())
                .andExpect(jsonPath("$.content[0].fromValute").value(convert2.getFromValute()))
                .andExpect(jsonPath("$.content[0].amount").value(convert2.getAmount() + ".0"))
                .andExpect(jsonPath("$.content[0].toValute").value(convert2.getToValute()))
                .andExpect(jsonPath("$.content[0].rate").value(convert2.getRate() + ".0"))
                .andExpect(jsonPath("$.content[0].result").value(convert2.getResult() + ".0"))

                .andExpect(jsonPath("$.content[1].id").value(convert1.getId()))
                .andExpect(jsonPath("$.content[1].date").isString())
                .andExpect(jsonPath("$.content[1].fromValute").value(convert1.getFromValute()))
                .andExpect(jsonPath("$.content[1].amount").value(convert1.getAmount() + ".0"))
                .andExpect(jsonPath("$.content[1].toValute").value(convert1.getToValute()))
                .andExpect(jsonPath("$.content[1].rate").value(convert1.getRate() + ".0"))
                .andExpect(jsonPath("$.content[1].result").value(convert1.getResult() + ".0"))

                .andExpect(jsonPath("$.pageable.pageNumber").value(0))
                .andExpect(jsonPath("$.pageable.pageSize").value(4))
                .andExpect(jsonPath("$.first").value("true"))
                .andExpect(jsonPath("$.last").value("true"))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.totalElements").value(2))
                .andExpect(jsonPath("$.number").value(0))
                .andExpect(jsonPath("$.numberOfElements").value(2))
                .andExpect(jsonPath("$.size").value(4))
                .andExpect(jsonPath("$.empty").value("false"));

        assertThat(convertRepository.findAll().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Конвертация не добавлена (не найден обменный курс)")
    public void saveFail() throws Exception {
        ConvertDto convertDto = ConvertDto.valueOf(createConvert());

        String convertDtoAsJson = objectMapper.writeValueAsString(convertDto);
        log.debug("convertDtoAsJson: " + convertDtoAsJson);

        mockMvc.perform(post(BASE_CONVERT_URL).content(convertDtoAsJson).contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());

        assertThat(convertRepository.findAll().size()).isEqualTo(0);
    }
}
