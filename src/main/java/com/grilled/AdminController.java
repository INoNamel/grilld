package com.grilled;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Controller
public class AdminController {

    @Autowired
    private HttpSession session;

    @Autowired
    private MenuRepository menuRepo;

    @Autowired
    private ClientsRepository clientsRepo;

    @Autowired
    private EmployeesRepository employeesRepo;

    @Autowired
    private OrdersRepository ordersRepo;

    @Autowired
    private LoginRepository loginRepo;

    @ExceptionHandler({DataIntegrityViolationException.class})
    public String handleMysqlDataTruncation(DataIntegrityViolationException ex) {
        return "error/500";
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public String handleIllegalArgumentException(IllegalArgumentException ex) {
        return "error/500";
    }

    @ExceptionHandler({NumberFormatException.class})
    public String handleTypeMismatch(NumberFormatException ex) {
        return "error/500";
    }

    @ExceptionHandler({DateTimeException.class})
    public String handleDateTimeException(DateTimeException ex) {
        return "error/500";
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public String handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        return "redirect:/";
    }

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
                case ("employees"):
                    if(session.getAttribute("auth_type").equals("admin")) {
                        model.addAttribute(directory, employeesRepo.findAllEmployees());
                        return "admin/show-employees";
                    }
                case ("restaurants"):
                    if(session.getAttribute("auth_type").equals("admin")) {
                        model.addAttribute(directory, menuRepo.findAllRestaurants());
                        return "admin/show-restaurants";
                    }
                case ("reservations"):
                    if(session.getAttribute("auth_type").equals("employee")) {
                        model.addAttribute(directory, ordersRepo.findAllReservations(null,null, false));
                        model.addAttribute("today", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                        return "admin/show-reservations";
                    }
                case ("takeaways"):
                    if(session.getAttribute("auth_type").equals("employee")) {
                        model.addAttribute(directory, ordersRepo.findAllTakeaways(null, null));
                        model.addAttribute("today", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                        return "admin/show-takeaways";
                    }
                default:
                    return "error/404";
            }
        } else {
            return "error/403";
        }
    }

    @GetMapping("/{profile}/{directory}/delete/{id}")
    public String deleteThis(@PathVariable(name = "profile") String profile, @PathVariable(name = "directory") String directory, @PathVariable(name = "id") int id, RedirectAttributes ra){
        if(session.getAttribute("logged") != null && session.getAttribute("logged").equals(true)) {
            ra.addFlashAttribute("message", "item #"+id+" deleted");
            switch (profile) {
                case ("my-orders"):
                    Login login = new Login();
                    login.setTlf_type(session.getAttribute("auth_type").toString());
                    switch (directory) {
                        case ("takeaways"):
                            if(ordersRepo.validateAction(login, id, "order_takeaway")) {
                                ordersRepo.deleteTakeaway(id);
                                return "redirect:/my-orders";
                            } else {
                                return "error/500";
                            }
                        case ("reservations"):
                            if(ordersRepo.validateAction(login, id, "order_table")) {
                                ordersRepo.deleteReservation(id);
                                return "redirect:/my-orders";
                            } else {
                                return "error/500";
                            }
                        default:
                            return "error/404";
                    }
                case ("admin"):
                    switch (directory) {
                        case ("dishes"):
                            if(session.getAttribute("auth_type").equals("admin")) {
                                menuRepo.deleteDish(id);
                                return "redirect:/admin/dishes";
                            }
                        case ("clients"):
                            if(session.getAttribute("auth_type").equals("admin")) {
                                clientsRepo.deleteClient(id);
                                return "redirect:/admin/clients";
                            }
                        case ("restaurants"):
                            if(session.getAttribute("auth_type").equals("admin")) {
                                menuRepo.deleteRestaurant(id);
                                return "redirect:/admin/restaurants";
                            }
                        case ("reservations"):
                            if(session.getAttribute("auth_type").equals("employee")) {
                                ordersRepo.deleteReservation(id);
                                return "redirect:/admin/reservations";
                            }
                        default:
                            return "error/404";
                    }
                default:
                    return "error/404";
            }
        } else {
            return "error/403";
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
                    return "error/404";
            }
        } else {
            return "error/403";
        }
    }

    @PostMapping("/admin/employees/add-employee")
    public String addEmployee(@ModelAttribute Login login, BindingResult result, RedirectAttributes ra) {
        if(session.getAttribute("logged") != null && session.getAttribute("logged").equals(true) && session.getAttribute("auth_type").equals("admin")) {
            if (result.hasErrors()) {
                ra.addFlashAttribute("message", "check input format");
                return "redirect:/admin/employees/add";
            } else {
                ra.addFlashAttribute("message", "new employee added");
                employeesRepo.addEmployee(login);
                return "redirect:/admin/employees";
            }
        } else {
            return "error/403";
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
            return "error/403";
        }
    }

    @PostMapping("/admin/restaurants/add-restaurant")
    public String addRestaurant(@ModelAttribute Restaurant restaurant, BindingResult result, RedirectAttributes ra) {
        if(session.getAttribute("logged") != null && session.getAttribute("logged").equals(true) && session.getAttribute("auth_type").equals("admin")) {
            if (result.hasErrors()) {
                ra.addFlashAttribute("message", "check input format");
                return "redirect:/admin/restaurants/add";
            } else {
                ra.addFlashAttribute("message", "new restaurant added");
                menuRepo.addRestaurant(restaurant);
                return "redirect:/admin/restaurants";
            }
        } else {
            return "error/403";
        }
    }

    @GetMapping("/admin/{directory}/edit/{id}")
    public String updateThis(@PathVariable(name = "directory") String directory, @PathVariable(name = "id") int id, Model model){
        if(session.getAttribute("logged") != null && session.getAttribute("logged").equals(true)) {
            switch (directory) {
                case ("dishes"):
                    if(session.getAttribute("auth_type").equals("admin")) {
                        model.addAttribute("meals", menuRepo.findAllMeals());
                        model.addAttribute("dishForm", menuRepo.findDish(id));
                        return "admin/edit-dish";
                    }
                case ("restaurants"):
                    if(session.getAttribute("auth_type").equals("admin")) {
                        model.addAttribute("restaurantForm", menuRepo.findRestaurant(null, id));
                        return "admin/edit-restaurant";
                    }
                case ("takeaways"):
                    if(session.getAttribute("auth_type").equals("employee")) {
                        Takeaway takeaway = ordersRepo.findTakeaway(id, null);
                        takeaway.setOrder(ordersRepo.findTakeawayOrders(takeaway));

                        Map<Integer, String> status_list = new HashMap<>();
                        status_list.put(0, "under review");
                        status_list.put(1, "cooking");
                        status_list.put(2, "ready for pickup");
                        status_list.put(3, "completed");

                        model.addAttribute("status_list", status_list);
                        model.addAttribute("takeawayForm", takeaway);
                        return "admin/edit-takeaway";
                    }
                default:
                    return "error/404";
            }
        } else {
            return "error/403";
        }
    }

    @PostMapping("/admin/restaurants/update-restaurant")
    public String updateRestaurant(@ModelAttribute Restaurant restaurant, BindingResult result, RedirectAttributes ra) {
        if(session.getAttribute("logged") != null && session.getAttribute("logged").equals(true) && session.getAttribute("auth_type").equals("admin")) {
            if (result.hasErrors()) {
                ra.addFlashAttribute("message", "check input format");
            } else {
                ra.addFlashAttribute("message", "restaurant updated");
                menuRepo.updateRestaurant(restaurant);
            }
            return "redirect:/admin/restaurants";
        } else {
            return "error/403";
        }
    }

    @PostMapping("/admin/takeaways/update-takeaway")
    public String updateTakeaway(@ModelAttribute Takeaway takeaway, BindingResult result, RedirectAttributes ra) {
        if(session.getAttribute("logged") != null && session.getAttribute("logged").equals(true) && session.getAttribute("auth_type").equals("employee")) {
            if (result.hasErrors()) {
                ra.addFlashAttribute("message", "check input format");
            } else {
                ra.addFlashAttribute("message", "takeaway status updated");
                ordersRepo.updateTakeaway(takeaway);
            }
            return "redirect:/admin/takeaways";
        } else {
            return "error/403";
        }
    }
    @PostMapping("/admin/dishes/update-dish")
    public String updateDish(@ModelAttribute Dish dish, BindingResult result, RedirectAttributes ra) {
        if(session.getAttribute("logged") != null && session.getAttribute("logged").equals(true) && session.getAttribute("auth_type").equals("admin")) {
            if (result.hasErrors()) {
                ra.addFlashAttribute("message", "check input format");
            } else {
                ra.addFlashAttribute("message", "dish updated");
                menuRepo.updateDish(dish);
            }
            return "redirect:/admin/dishes";
        } else {
            return "error/403";
        }
    }
}
