package me.ikapkova.recipesbook.services;

import com.fasterxml.jackson.core.type.TypeReference;
import me.ikapkova.recipesbook.dto.IngredientDTO;
import me.ikapkova.recipesbook.exceptions.IngredientNoFounException;
import me.ikapkova.recipesbook.exceptions.RecipeNoFounException;
import me.ikapkova.recipesbook.model.Ingredient;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.Map.Entry;
@Service
public class IngredientService {
    private static final String STORE_FILE_NAME = "ingredients";
    private static int idCounter = 0;
    private final FileService fileService;

    private Map<Integer, Ingredient> ingredients;

    public IngredientService(FileService fileService) {
        this.fileService = fileService;
        Map<Integer, Ingredient> storeMap = fileService.readFromFile(STORE_FILE_NAME, new TypeReference<>() {});
        this.ingredients = Objects.requireNonNullElseGet(storeMap, HashMap::new);
    }


    public IngredientDTO addIngredient(Ingredient ingredient) {
        int id = idCounter++;
        ingredients.put(id, ingredient);
        fileService.saveToFile(STORE_FILE_NAME, ingredients);
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
        fileService.saveToFile(STORE_FILE_NAME, ingredients);
        return IngredientDTO.from(id, recipe);
    }

    public IngredientDTO deleteById(int id) {
        Ingredient existingIngredient = ingredients.remove(id);
        if (existingIngredient == null) {
            throw new RecipeNoFounException();
        }
        fileService.saveToFile(STORE_FILE_NAME, ingredients);
        return IngredientDTO.from(id, existingIngredient);
    }

    public IngredientDTO updateIngredient(int id, Ingredient ingredient) {
        Ingredient existingIngredient = ingredients.get(id);
        if (existingIngredient == null) {
            throw new IngredientNoFounException();
        }
    ingredients.put(id, ingredient);
    return IngredientDTO.from(id, ingredient);
    }
//
    public  void importIngrediens (Resource resource){
        fileService.saveRecource(STORE_FILE_NAME, resource);
        this.ingredients = fileService.readFromFile(STORE_FILE_NAME, new TypeReference<>() {
        });
    }

}
