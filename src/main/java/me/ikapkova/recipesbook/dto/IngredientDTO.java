package me.ikapkova.recipesbook.dto;

import lombok.Data;
import me.ikapkova.recipesbook.model.Ingredient;

@Data
public class IngredientDTO {
    private final  int id;
    private final  String name;
    private  final Integer count;
    private  final String MeasureUnit;


    public static IngredientDTO from(int id, Ingredient ingredient){
        return new IngredientDTO(id, ingredient.getName(), ingredient.getCount(), ingredient.getMeasureUnit());
    }
}