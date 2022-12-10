package com.example.demo.service;

import com.example.demo.dto.ConvertDto;
import org.springframework.data.domain.Page;

/**
 * @author Aleksey Zhdanov
 * @version 1
 */
public interface ConvertService {

    /**
     * <p>Возвращает страницу с конвертациями</p>
     *
     * @param pageIndex Номер страницы
     * @return Страница с конвертациями
     */
    Page<ConvertDto> findConvertPage(Integer pageIndex);

    /**
     * <p>Добавляет новую конвертацию в историю</p>
     *
     * @param convertDto Данные конвертации для добавления
     * @return Сохранённая в БД конвертация
     */
    ConvertDto save(ConvertDto convertDto);
}
