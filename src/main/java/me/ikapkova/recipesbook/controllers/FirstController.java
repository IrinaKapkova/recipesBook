package me.ikapkova.recipesbook.controllers;
import me.ikapkova.recipesbook.dto.InfoDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.time.LocalDate;

@RestController
@RequestMapping("/")
public class FirstController {
    private static final InfoDTO INFO = new InfoDTO(
            "Irina Kapkova",
            "Recipe Web site",
            LocalDate.of(2023,2,26),
            "Creation, addition,editing, view, deleting recipes and ingredients");
    @GetMapping
    public String applicationLaunched() {
        return "Приложение запущено!";
    }
    @GetMapping(value = "/info")
    public InfoDTO info(){
        return  INFO;
    }

//    @GetMapping("/info")
//    public String info() {
//        return "Информация: " + "<br>" +
//                " имя автора: Ирина <br> " +
//                " название проекта:  Веб приложение  <br>" +
//                " дата создания проекта: 26.02.2023г. <br>" +
//                " Веб сайт рецептов";
//    }

    @GetMapping("/info/datainput")
    public String dataInput(@RequestParam String name, @RequestParam String project) {
        return "Информация: " + "<br>" +
                " имя ученика: " + name + "<br>" +
                " название проекта:  " + project + "<br>" +
                " дата создания проекта: 26.02.2023. " + "<br>" +
                " Веб приложение. ";
        //  Пример в браузере: http://localhost:8080/info/datainput?name=Ira&project=RecipeWebsite
    }
}