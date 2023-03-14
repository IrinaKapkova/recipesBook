package me.ikapkova.recipesbook.services;

import me.ikapkova.recipesbook.dto.IngredientDTO;
import me.ikapkova.recipesbook.exceptions.IngredientNoFounException;
import me.ikapkova.recipesbook.exceptions.RecipeNoFounException;
import me.ikapkova.recipesbook.model.Ingredient;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
@Service
public class IngredientService {
    private static int idCounter = 0;

    private final Map<Integer, Ingredient> ingredients = new HashMap<>();

    public IngredientDTO addIngredient(Ingredient ingredient) {
        int id = idCounter++;
        ingredients.put(id, ingredient);
        return IngredientDTO.from(id, ingredient);
    }

    public IngredientDTO getIngredient(int id) {
        Ingredient ingredient = ingredients.get(id);
        if (ingredient != null) {
            return IngredientDTO.from(id, ingredient);
        }
        return null;
    }

    public List<IngredientDTO> getAllIngredients() {
        List<IngredientDTO> result = new ArrayList<>();
        for (Entry<Integer, Ingredient> entry : ingredients.entrySet()) {
            result.add(IngredientDTO.from(entry.getKey(), entry.getValue()));
        }
        return result;
    }

    public IngredientDTO updateRecipe(int id, Ingredient recipe) {
        Ingredient existingIngredient = ingredients.get(id);
        if (existingIngredient == null) {
            throw new RecipeNoFounException();
        }
        ingredients.put(id, recipe);
        return IngredientDTO.from(id, recipe);
    }

    public IngredientDTO deleteById(int id) {
        Ingredient existingIngredient = ingredients.remove(id);
        if (existingIngredient == null) {
            throw new RecipeNoFounException();
        }
        return IngredientDTO.from(id, existingIngredient);
    }

    public void addIngredient(String name, int count, String measureUnit) {
    }


    public IngredientDTO updateIngredient(int id, Ingredient ingredient) {
        Ingredient existingIngredient = ingredients.get(id);
        if (existingIngredient == null) {
            throw new IngredientNoFounException();
        }
    ingredients.put(id, ingredient);
    return IngredientDTO.from(id, ingredient);
    }

}
