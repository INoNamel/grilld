package com.grilled;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {

    @Autowired
    private MenuRepository menuRepo;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("dishes", menuRepo.findAllDishes(true, null));
        return "index";
    }
}
