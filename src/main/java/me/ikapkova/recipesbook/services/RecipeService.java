package me.ikapkova.recipesbook.services;

import me.ikapkova.recipesbook.dto.IngredientDTO;
import me.ikapkova.recipesbook.dto.RecipeDTO;
import me.ikapkova.recipesbook.exceptions.RecipeNoFounException;
import me.ikapkova.recipesbook.exceptions.RecipeValidationException;
import me.ikapkova.recipesbook.model.Ingredient;
import me.ikapkova.recipesbook.model.Recipe;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecipeService {
    private int idCounter = 0;
    private final IngredientService  ingredientService;
    private final Map<Integer, Recipe> recipes = new HashMap<>();

    public RecipeService(IngredientService ingrediantService) {
        this.ingredientService = ingrediantService;
    }

    public RecipeDTO addRecipe(Recipe recipe) {
        if(StringUtils.isBlank(recipe.getRecipeName())){
            throw new RecipeValidationException();
        }
        int id = idCounter++;
        recipes.put(id, recipe);
        for (Ingredient ingredient : recipe.getIngredients()) {
            this.ingredientService.addIngredient(ingredient);
        }
        return RecipeDTO.from(id, recipe);
    }

    public RecipeDTO getRecipe(int id) {
        Recipe recipe = recipes.get(id);
        if (recipe != null) {
            return RecipeDTO.from(id, recipe);
        }
        return null;
    }

    public List<RecipeDTO> getAllRecipe() {
        List<RecipeDTO> result = new ArrayList<>();
        for (Map.Entry<Integer, Recipe> entry : recipes.entrySet()) {
            result.add(RecipeDTO.from(entry.getKey(), entry.getValue()));
        }
        return result;
    }

    public RecipeDTO updateRecipe(int id, Recipe recipe) {
        Recipe existingRecipe = recipes.get(id);
        if (existingRecipe == null) {
            throw new RecipeNoFounException();
        }
        recipes.put(id, recipe);
        return RecipeDTO.from(id, recipe);
    }

    public RecipeDTO deleteById(int id) {
        Recipe existingRecipe = recipes.remove(id);
        if (existingRecipe == null) {
            throw new RecipeNoFounException();
        }
        return RecipeDTO.from(id, existingRecipe);
    }

    public List<RecipeDTO> getRecipesByIngredientId(int ingredientId) {
        IngredientDTO ingrediet = this.ingredientService.getIngredient(ingredientId);
        //
        return this.recipes.entrySet()
                .stream()
                .filter(e -> e.getValue().getIngredients().stream().anyMatch(i -> i.getName().equals(ingrediet.getName())))
                .map(e -> RecipeDTO.from(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }

    public List<RecipeDTO> getRecipesByIngredientsIds(List<Integer> ingredientsIds) {
        List<String> ingredientsNames = ingredientsIds.stream()
                .map(this.ingredientService::getIngredient)
                .filter(Objects::nonNull)
                .map(IngredientDTO::getName)
                .toList();
        return this.recipes.entrySet()
                .stream()
                .filter(e -> {
                    Set<String> recipeIngredientsNames = e.getValue()
                            .getIngredients()
                            .stream()
                            .map(Ingredient::getName)
                            .collect(Collectors.toSet());
                    return recipeIngredientsNames.containsAll(ingredientsNames);
                })
                .map(e -> RecipeDTO.from(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }
}