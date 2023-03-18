package me.ikapkova.recipesbook.controllers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.ikapkova.recipesbook.dto.InfoDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.time.LocalDate;

@RestController
@RequestMapping("/")
@Tag(name = "Информация о приложении", description = "выводит информацию об авторе , дате создания и предназначении сайта")
public class FirstController {
    private static final InfoDTO INFO = new InfoDTO(
            "Irina Kapkova",
            "Recipes Book",
            LocalDate.of(2023,2,26),
            "Creation, addition,editing, view, deleting recipes and ingredients");
    @Operation(summary = "Выводит информационную строку")
    @GetMapping
    public String applicationLaunched() {
        return "Приложение запущено!";
    }
    @Operation(summary = "Выводит информацию об авторе , дате создания и предназначении сайта")
    @GetMapping(value = "/info")
    public InfoDTO info(){
        return  INFO;
    }

@Operation(
        summary = "Подставляет внесенные параметры пользователем в информацию об авторе , дате создания и предназначении сайта"
)
@Parameters (value ={@Parameter
        (name= "name", example ="Иван Иванов"),
        @Parameter
                (name= "project", example ="Проект по хранению данных")}
)
@ApiResponses(
        value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "информация о проекте сформирована и выведена на просмотр"
                ),

        }
)
    @GetMapping("/info/datainput")
    public String dataInput(@RequestParam String name, @RequestParam String project) {
        return "Информация: " + "<br>" +
                " имя автора: " + name + "<br>" +
                " название проекта:  " + project + "<br>" +
                " дата создания проекта: 26.02.2023. " + "<br>" +
                " Веб приложение. ";
        //  Пример в браузере: http://localhost:8080/info/datainput?name=Ira&project=RecipeWebsite
    }
}