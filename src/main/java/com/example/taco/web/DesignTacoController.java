package com.example.taco.web;

//import com.example.taco.Design;
import com.example.taco.Design;
import com.example.taco.Ingredient;
import com.example.taco.Order;
import com.example.taco.Taco;
import com.example.taco.data.IngredientRepository;
import com.example.taco.data.TacoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {
    private IngredientRepository ingredientRepo;
    private TacoRepository designRepo;
    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepo,TacoRepository designRepo) {
        this.designRepo = designRepo;
        this.ingredientRepo = ingredientRepo;
    }

    @GetMapping
    public String showDesignForm(Model model){
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepo.findAll().forEach(ingredients::add);
        Ingredient.Type[] types = Ingredient.Type.values();
        for (Ingredient.Type type : types) {
            model.addAttribute(type.toString().toLowerCase(),filterByType(ingredients,type));
        }
        model.addAttribute("design", new Taco());
        return "design";
    }
    @PostMapping
    public String processDesign(@Valid Taco design,Errors errors,@ModelAttribute Order order) throws SQLException {
        if (errors.hasErrors()) {
            return "design";
        }
        Taco saved = designRepo.save(design);
        order.addDesign(saved);
        return "redirect:/orders/current";
    }
    @ModelAttribute(name = "order")
    public Order order() {
        return new Order();
    }
    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
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