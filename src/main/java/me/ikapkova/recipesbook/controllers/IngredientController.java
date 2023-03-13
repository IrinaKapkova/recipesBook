package me.ikapkova.recipesbook.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import me.ikapkova.recipesbook.services.IngredientService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ingredient")
@Tag(name = "Ингредиенты", description = "CRUD-операции для работы с ингредиентами")
public class IngredientController {
    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }
    @Operation(summary = "Добавление ингредиента")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент успешно добавлен"
            )
    })
    @PostMapping()
    public String addIngredients(@RequestParam String name, @RequestParam int count, @RequestParam String measureUnit) {
        ingredientService.addIngredient(name, count, measureUnit);
        return "Был добавлен ингридиент:" + name + " " + count + " " + measureUnit;
    }
}