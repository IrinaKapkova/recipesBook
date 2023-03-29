package me.ikapkova.recipesbook.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.*;

@AllArgsConstructor
@Data
@ToString
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
}