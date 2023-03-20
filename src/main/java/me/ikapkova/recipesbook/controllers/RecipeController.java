package me.ikapkova.recipesbook.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.ikapkova.recipesbook.dto.RecipeDTO;
import me.ikapkova.recipesbook.model.Recipe;
import me.ikapkova.recipesbook.services.RecipeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipe")
@Tag(name = "Рецепты", description = "CRUD-операции для работы с рецептами")
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }
    @Operation(
            summary = "выводит информацию о всех рецептах"
    )

    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "информация о рецептах сформирована и выведена на просмотр"
                    ),

            }
    )
    @GetMapping
    public List<RecipeDTO> getRecipes(){
        return  recipeService.getAllRecipe();
    }
    @Operation(
            summary = "выводит информацию о рецепте по id"
    )

    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "информация о рецепте сформирована и выведена на просмотр"
                    ),

            }
    )
    @GetMapping ("/{id}")
    public RecipeDTO getRecipe(@PathVariable("id") int id){
        return recipeService.getRecipe(id);
    }

    @Operation(summary = "Добавление рецепта")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт успешно добавлен"
            )
    })
    @PostMapping
    public  RecipeDTO addRecipe (@RequestBody Recipe recipe){
        return recipeService.addRecipe(recipe);
    }
    @Operation(summary = "Редактирование рецепта по id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт успешно изменен"
            )
    })
    @PutMapping ("/{id}")
    public RecipeDTO editRecipe (@PathVariable("id") int id, @RequestBody Recipe recipe){
        return  recipeService.updateRecipe(id, recipe);
    }
    @Operation(summary = "Удаление рецепта по id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт успешно удален"
            )
    })
    @DeleteMapping("/{id}")
    public RecipeDTO deleteRecipe(@PathVariable("id") int id){
        return recipeService.deleteById (id);
    }
    @Operation(summary = "Выводит все рецепты содержащие определенный по id ингридиент")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт успешно найден"
            )
    })
    @GetMapping("/byIngredient/{id}")
    public List<RecipeDTO>getByIngredientId(@PathVariable("id") int id){
        return  recipeService.getRecipesByIngredientId(id);
    }
    @Operation(
            summary = "выводит информацию о рецептах содержащих в своем составе ингридиенты с указанными в запросе id"
    )

    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "информация о рецептах сформирована и выведена на просмотр"
                    ),

            }
    )
    @GetMapping("/byIngredients")
    public List<RecipeDTO>getByIngredientsIds (@RequestParam ("Ids") List<Integer> ids){
        return   recipeService.getRecipesByIngredientsIds(ids);
    }
    @Operation(
            summary = "выводит информацию о рецептах с указанной страницы , на одной странице не боллее 10 рецептов"
    )

    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "указанная страница с рецептами сформирована и выведена на просмотр"
                    ),

            }
    )
    @GetMapping("/page/{pageNunber}")
    public List<RecipeDTO> getPage(@PathVariable ("pageNunber") int pageNumber){
        return recipeService
                .getAllRecipe()
                .stream()
                .skip((pageNumber-1)*10L)
                .limit(10).toList();
    }
}
