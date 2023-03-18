package me.ikapkova.recipesbook.dto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
@Data
@AllArgsConstructor
public class InfoDTO {
    private final String author;
    private final String name;
    private final LocalDate creationDate;

    private final String description;

}