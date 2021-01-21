package com.example.taco.web;

//import com.example.taco.Design;
import com.example.taco.Design;
import com.example.taco.Ingredient;
import com.example.taco.Order;
import com.example.taco.Taco;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/design")
public class DesignTacoController {

    @GetMapping
    public String showDesignForm(Model model){
        List<Ingredient> ingredients = Arrays.asList(
                new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP),
                new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP),
                new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN),
                new Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN));
        Ingredient.Type[] types = Ingredient.Type.values();
        for (Ingredient.Type type : types) {
            model.addAttribute(type.toString().toLowerCase(),filterByType(ingredients,type));
        }
        model.addAttribute("design", new Taco());
        return "design";
    }
    @PostMapping
    public String processDesign(Taco design){
        log.info("Processing design" + design);
        return "redirect:/orders/current";
    }


    private List<Ingredient> filterByType(List<Ingredient> ingredients, Ingredient.Type type) {
        List<Ingredient>listIngredient = new ArrayList<>();
        switch (type){
            case WRAP:
                listIngredient.add(ingredients.get(0));
                listIngredient.add(ingredients.get(1));
                break;
            case PROTEIN:
                listIngredient.add(ingredients.get(2));
                listIngredient.add(ingredients.get(3));
                break;
        }
        return listIngredient;
    }

}