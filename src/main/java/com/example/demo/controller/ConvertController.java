package com.example.demo.controller;

import com.example.demo.dto.ConvertDto;
import com.example.demo.service.ConvertService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/convert")
@Tag(name = "Конвертация валют")
public class ConvertController {

    private final ConvertService convertService;

    @Autowired
    public ConvertController(ConvertService convertService) {
        this.convertService = convertService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Получить страницу с конвертациями")
    public Page<ConvertDto> findConvertPage(@RequestParam("pageIndex") Integer pageIndex) {
        return convertService.findConvertPage(pageIndex);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Добавить новую конвертацию в историю")
    public ConvertDto save(@RequestBody @Valid ConvertDto convertDto) {
        return convertService.save(convertDto);
    }
}
