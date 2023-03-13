package me.ikapkova.recipesbook.controllers;


import me.ikapkova.recipesbook.dto.RecipeDTO;
import me.ikapkova.recipesbook.model.Recipe;
import me.ikapkova.recipesbook.services.RecipeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipe")
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }
    @GetMapping
    public List<RecipeDTO> getRecipes(){
        return  recipeService.getAllRecipe();
    }
    @GetMapping ("/{id}")
    public RecipeDTO getRecipe(@PathVariable("id") int id){
        return recipeService.getRecipe(id);
    }
    @PostMapping
    public  RecipeDTO addRecipe (@RequestBody Recipe recipe){
        return recipeService.addRecipe(recipe);
    }
    @PutMapping ("/{id}")
    public RecipeDTO editRecipe (@PathVariable("id") int id, @RequestBody Recipe recipe){
        return  recipeService.updateRecipe(id, recipe);
    }
    @DeleteMapping("/{id}")
    public RecipeDTO deleteRecipe(@PathVariable("id") int id){
        return recipeService.deleteById (id);
    }
    @GetMapping("/byIngredient/{id}")
    public List<RecipeDTO>getByIngredientId(@PathVariable("id") int id){
        return  recipeService.getRecipesByIngredientId(id);
    }
    @GetMapping("/byIngredients")
    public List<RecipeDTO>getByIngredientsIds (@RequestParam ("Ids") List<Integer> ids){
        return   recipeService.getRecipesByIngredientsIds(ids);
    }
    @GetMapping("/page/{pageNunber}")
    public List<RecipeDTO> getPage(@PathVariable int pageNumber){
        return recipeService
                .getAllRecipe()
                .stream()
                .skip(pageNumber*10L)
                .limit(10).toList();
    }
}
