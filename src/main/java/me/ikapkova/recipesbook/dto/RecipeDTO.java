package me.ikapkova.recipesbook.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import me.ikapkova.recipesbook.model.Ingredient;
import me.ikapkova.recipesbook.model.Recipe;


import java.util.List;

@Data
@AllArgsConstructor
@Getter

public class RecipeDTO {
    private final int id;
    private String recipeName;
    private int cookingTime;
    private final List<Ingredient> ingredients;
    private List<String> stepsOfCooking;

    public static RecipeDTO from(int id, Recipe recipe){
        return  new RecipeDTO(id, recipe.getRecipeName(), recipe.getCookingTime(), recipe.getIngredients(), recipe.getStepsOfCooking());
    }
}

