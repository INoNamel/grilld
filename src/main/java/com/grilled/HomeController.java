package com.grilled;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    @Autowired
    private HttpSession session;

    @Autowired
    private LoginRepository loginRepo;

    @Autowired
    private MenuRepository menuRepo;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("dishes", menuRepo.findAllDishes(true, null));
        return "index";
    }
}
