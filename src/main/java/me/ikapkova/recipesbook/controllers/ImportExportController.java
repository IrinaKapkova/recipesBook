package me.ikapkova.recipesbook.controllers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import me.ikapkova.recipesbook.services.IngredientService;
import me.ikapkova.recipesbook.services.RecipeService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
@RestController
public class ImportExportController {
    private final RecipeService recipeService;
    private final IngredientService ingredientService;

    public ImportExportController(RecipeService recipeService, IngredientService ingredientService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
    }

    @GetMapping("/files/export/recipes")
    @Operation(summary = "Скачивание файла рецептов в JSON формате.")
    @ApiResponse(responseCode = "200", description = "Файл экспортирован.")
    public ResponseEntity<Resource> downloadRecipes() throws IOException {
        Resource recipes = recipeService.getRecipesFiles();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"recipes.json\"")
                .contentLength(recipes.contentLength())
                .contentType(MediaType.TEXT_PLAIN)
                .body(recipes);
    }
    @PutMapping(value = "/files/import/recipes", consumes = MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Импорт рецептов.", description = "Импорт файла рецептов в .json формате.")
    @ApiResponse(responseCode = "200", description = "Файл загружен.")
    public ResponseEntity<?> importRecipes(@RequestParam MultipartFile file) {
        this.recipeService.importRecipes(file.getResource());
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/files/import/ingredients", consumes = MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Импорт ингридиентов.", description = "Импорт файла ингридиентов в .json формате.")
    @ApiResponse(responseCode = "200", description = "Файл загружен.")
    public ResponseEntity<?> importIngredients(@RequestParam MultipartFile file) {
        this.ingredientService.importIngrediens(file.getResource());
        return ResponseEntity.ok().build();
    }
}
