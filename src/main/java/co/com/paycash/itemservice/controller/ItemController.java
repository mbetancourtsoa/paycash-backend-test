package co.com.paycash.itemservice.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.com.paycash.itemservice.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/titles", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
@RequiredArgsConstructor
@Tag(name = "Items", description = "Operaciones sobre items")
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    @Operation(summary = "Obtiene los títulos de items cuyo rating promedio es menor al indicado")
    public List<String> getTitles(@Parameter(description = "Rating umbral", required = true) @RequestParam Double rating) {
        return itemService.getTitles(rating);
    }
}
