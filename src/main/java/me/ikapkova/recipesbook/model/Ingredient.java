package me.ikapkova.recipesbook.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter

public class Ingredient {

    @NotBlank(message = "наименование ингредиента не может быть null и должен содержать хотя бы один непробельный символ")
    @Size(min = 2, max = 30, message = "наименование ингредиента должно содержать не менее 2 и не более 30 символов")
    private String name;
    @Positive
    private Integer count;
    private String MeasureUnit;
}


