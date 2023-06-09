package me.ikapkova.recipesbook.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.*;

@AllArgsConstructor
@Data
public class Recipe {

    @NotBlank(message = "наименование рецепта не может быть null и должно содержать хотя бы один непробельный символ")
    @Size(min = 3, max = 30, message = "наименование рецепта должно содержать не менее 3 и не более 30 символов")

    private String recipeName;
    @Positive(message = "время приготовления должно быть больше нуля")
    private int cookingTime;
    @NotEmpty(message = "список ингредиентов не должен быть null или пустым")
    private List <Ingredient> ingredients;
    @NotEmpty (message = "Инструкция по приготовлению не должна быть null или пустой")
    private List<String> stepsOfCooking;
    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append(recipeName).append("\n");
        buffer.append("Время приготовления: " + cookingTime + " мин.").append("\n");
        buffer.append("Ингредиенты: ").append("\n");
        for (Ingredient ingredient : ingredients) {
            buffer.append("\t").append(ingredient).append("\n");
        }
        buffer.append("Инструкция приготовления: ").append("\n");
        for (int i = 0; i < stepsOfCooking.size(); i++) {
            buffer.append(i + 1).append(". ").append(stepsOfCooking.get(i)).append("\n");
        }
        return buffer.toString();
    }
}