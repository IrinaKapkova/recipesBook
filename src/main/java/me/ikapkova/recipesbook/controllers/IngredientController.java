package me.ikapkova.recipesbook.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.ikapkova.recipesbook.dto.IngredientDTO;
import me.ikapkova.recipesbook.model.Ingredient;
import me.ikapkova.recipesbook.services.IngredientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ingredient")
@Tag(name = "Ингредиенты", description = "CRUD-операции для работы с ингредиентами")
public class IngredientController {
    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping
    public List<IngredientDTO> getIngredients() {
        return ingredientService.getAllIngredients();
    }

    @GetMapping("/{id}")
    public IngredientDTO getIngredients(@PathVariable("id") int id) {
        return ingredientService.getIngredient(id);
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
    @Operation(summary = "Изменение ингредиента")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент успешно изменен"
            )
    })
    @PutMapping("/{id}")
    public IngredientDTO editIngredient(@PathVariable("id") int id, @RequestBody Ingredient ingredient) {
        return ingredientService.updateIngredient(id, ingredient);
    }
    @Operation(summary = "Удаление ингредиента")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент успешно удален"
            )
    })
    @DeleteMapping("/{id}")
    public IngredientDTO deleteIngredient(@PathVariable("id") int id) {
        return ingredientService.deleteById(id);
    }
}