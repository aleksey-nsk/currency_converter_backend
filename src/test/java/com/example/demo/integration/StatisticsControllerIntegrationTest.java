package com.example.demo.integration;

import com.example.demo.dto.StatisticsDto;
import com.example.demo.entity.Statistics;
import com.example.demo.util.StatisticsDtoComparator;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@Log4j2
public class StatisticsControllerIntegrationTest extends BaseIntegrationTest {

    @Test
    @DisplayName("Успешный поиск всей статистики по конвертациям")
    public void findAllSuccess() throws Exception {
        Statistics saved1 = saveStatisticsInDB();
        Statistics saved2 = saveStatisticsInDB();
        assertThat(statisticsRepository.findAll().size()).isEqualTo(2);

        List<Statistics> list = new ArrayList<>();
        list.add(saved1);
        list.add(saved2);
        log.debug("list: " + list);

        List<StatisticsDto> sortedList = list.stream()
                .map(it -> StatisticsDto.valueOf(it))
                .sorted(new StatisticsDtoComparator())
                .collect(Collectors.toList());
        log.debug("sortedList: " + sortedList);

        String savedAsJson = objectMapper.writeValueAsString(sortedList);
        log.debug("savedAsJson: " + savedAsJson);

        mockMvc.perform(get(BASE_STATISTICS_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(savedAsJson, true));
        assertThat(statisticsRepository.findAll().size()).isEqualTo(2);
    }
}
