package me.ikapkova.recipesbook.services;

import com.fasterxml.jackson.core.type.TypeReference;
import me.ikapkova.recipesbook.dto.IngredientDTO;
import me.ikapkova.recipesbook.dto.RecipeDTO;
import me.ikapkova.recipesbook.exceptions.RecipeNoFounException;
import me.ikapkova.recipesbook.exceptions.RecipeValidationException;
import me.ikapkova.recipesbook.model.Ingredient;
import me.ikapkova.recipesbook.model.Recipe;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.TreeMap;

@Service
public class RecipeService {
    private int idCounter = 0;
    private final IngredientService  ingredientService;
    private Map<Integer, Recipe> recipes;
    private final FileService fileService;

    private final  static String STORE_FILE_NAME = "recipes";

    public RecipeService(IngredientService ingrediantService, FileService fileService) {
        this.ingredientService = ingrediantService;
        this.fileService = fileService;
        Map<Integer,Recipe> storeMap = fileService.readFromFile(STORE_FILE_NAME, new TypeReference<>() {});
        this.recipes = Objects.requireNonNullElseGet(storeMap, HashMap::new);
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
        this.fileService.saveToFile(STORE_FILE_NAME, this.recipes);
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
        this.fileService.saveToFile(STORE_FILE_NAME, this.recipes);
        return RecipeDTO.from(id, recipe);
    }

    public RecipeDTO deleteById(int id) {
        Recipe existingRecipe = recipes.remove(id);
        if (existingRecipe == null) {
            throw new RecipeNoFounException();
        }
        this.fileService.saveToFile(STORE_FILE_NAME, this.recipes);
        return RecipeDTO.from(id, existingRecipe);
    }

    public Resource getRecipesFiles() {
        return fileService.getRecource(STORE_FILE_NAME);
    }

    public  void importRecipes (Resource resource){
       fileService.saveRecource(STORE_FILE_NAME, resource);
       this.recipes = fileService.readFromFile(STORE_FILE_NAME, new TypeReference<>() {
       });
    }

    public List<RecipeDTO> getRecipesByIngredientId(int ingredientId) {
        IngredientDTO ingrediet = this.ingredientService.getIngredient(ingredientId);
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
    public Resource getRecipesFilesTxt () {
        return fileService.getRecourceTxt(STORE_FILE_NAME);
    }

}