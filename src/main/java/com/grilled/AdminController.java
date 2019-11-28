package com.grilled;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
public class AdminController {

    @Autowired
    private HttpSession session;

    @Autowired
    private MenuRepository menuRepo;

    @Autowired
    private ClientsRepository clientsRepo;

    @Autowired
    private OrdersRepository ordersRepo;

    @Autowired
    private LoginRepository loginRepo;


    @GetMapping("/admin/{directory}")
    public String navigateTo(@PathVariable(name = "directory") String directory, Model model){
        if(session.getAttribute("logged") != null && session.getAttribute("logged").equals(true)) {
            switch (directory) {
                case ("dishes"):
                    if(session.getAttribute("auth_type").equals("admin")) {
                        model.addAttribute(directory, menuRepo.findAllDishes(false, null));
                        return "admin/show-dishes";
                    }
                case ("clients"):
                    if(session.getAttribute("auth_type").equals("admin")) {
                        model.addAttribute(directory, clientsRepo.findAllClients());
                        return "admin/show-clients";
                    }
                case ("reservations"):
                    if(session.getAttribute("auth_type").equals("employee")) {
                        model.addAttribute(directory, ordersRepo.findAllReservations(1));
                        return "admin/show-reservations";
                    }
                case ("takeaways"):
                    if(session.getAttribute("auth_type").equals("employee")) {
                        model.addAttribute(directory, ordersRepo.findAllTakeaways(1));
                        return "admin/show-takeaways";
                    }
                default:
                    return "redirect:/";
            }
        } else {
            return "redirect:/admin";
        }
    }

    @GetMapping("/admin/{directory}/delete/{id}")
    public String deleteThis(@PathVariable(name = "directory") String directory, @PathVariable(name = "id") String id, RedirectAttributes ra){
        if(session.getAttribute("logged") != null && session.getAttribute("logged").equals(true)) {
            ra.addFlashAttribute("message", "#"+id+" deleted");
            switch (directory) {
                case ("dishes"):
                    menuRepo.deleteDish(Integer.parseInt(id));
                    return "redirect:/admin/dishes";
                case ("clients"):
                    clientsRepo.deleteClient(Integer.parseInt(id));
                    return "redirect:/admin/clients";
                case ("reservations"):
                    ordersRepo.deleteReservation(Integer.parseInt(id));
                    return "redirect:/admin/reservations";
                case ("takeaways"):
                    ordersRepo.deleteTakeaway(Integer.parseInt(id));
                    return "redirect:/admin/takeaways";
                default:
                    return "redirect:/";
            }
        } else {
            return "redirect:/admin";
        }
    }

    @GetMapping("/admin/{directory}/add")
    public String saveThis(@PathVariable(name = "directory") String directory, Model model) {
        if(session.getAttribute("logged") != null && session.getAttribute("logged").equals(true) && session.getAttribute("auth_type").equals("admin")) {
            switch (directory) {
                case ("dishes"):
                    Dish dish = new Dish();
                    dish.setMeal(new Meal());

                    model.addAttribute("meals", menuRepo.findAllMeals());
                    model.addAttribute("dishForm", dish);
                    return "admin/add-dish";
                case ("restaurants"):
                    model.addAttribute("restaurantForm", new Restaurant());
                    return "admin/add-restaurant";
                case ("employees"):
                    model.addAttribute("employeeForm", new Login());
                    return "admin/add-employee";
                default:
                    return "redirect:/";
            }
        } else {
            return "redirect:/admin";
        }
    }

    @PostMapping("/admin/dishes/add-dish")
    public String addDish(@ModelAttribute Dish dish, BindingResult result, RedirectAttributes ra) {
        if(session.getAttribute("logged") != null && session.getAttribute("logged").equals(true) && session.getAttribute("auth_type").equals("admin")) {
            if (result.hasErrors()) {
                ra.addFlashAttribute("message", "check input format");
                return "redirect:/admin/dishes/add";
            } else {
                ra.addFlashAttribute("message", "new dish added");
                menuRepo.addDish(dish);
                return "redirect:/admin/dishes";
            }
        } else {
            return "redirect:/admin";
        }
    }
}
