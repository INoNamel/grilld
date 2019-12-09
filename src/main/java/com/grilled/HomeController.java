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
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.ArrayList;

@Controller
public class HomeController {

    @Autowired
    private HttpSession session;

    @Autowired
    private LoginRepository loginRepo;

    @Autowired
    private MenuRepository menuRepo;

    @Autowired
    private OrdersRepository ordersRepo;


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

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("dishes", menuRepo.findAllDishes(true, null));
        return "index";
    }

    @GetMapping("/reservation")
    public String reservation_at(Model model) {
        model.addAttribute("restaurants", menuRepo.findAllRestaurants());
        return "reservation-at";
    }

    @GetMapping("/reservation/{restaurant}")
    public String reservation(@PathVariable(name = "restaurant") String restaurant_town, Model model) {
        Restaurant restaurant = menuRepo.findRestaurant(restaurant_town.toLowerCase());
        if(restaurant != null && restaurant.getId()>0){
            Reservation reservation = new Reservation();
            reservation.setLogin(new Login());
            reservation.setRestaurant(restaurant);
            reservation.setOrder_for(LocalDateTime.now().plus(15, ChronoUnit.MINUTES));
            model.addAttribute("reservationForm", reservation);
        } else {
            return "error/404";
        }
        return "reservation";
    }

    @PostMapping("/reservation-confirm")
    public String reservation_confirm(@ModelAttribute Reservation reservation, BindingResult result, RedirectAttributes ra) {
        if(session.getAttribute("logged") != null && session.getAttribute("logged").equals(true)) {
            if (result.hasErrors()) {
                ra.addFlashAttribute("message", "check input format");
            } else {
                reservation.setLogin(new Login());
                reservation.getLogin().setTlf_type(session.getAttribute("auth_type").toString());
                ra.addFlashAttribute("message", "new reservation added");
                ordersRepo.addReservation(reservation);
            }
            return "redirect:/";
        } else {
            ra.addFlashAttribute("message", "log in before reserving");
            return "redirect:/login";
        }
    }

    @GetMapping("/take-away")
    public String takeaway(Model model) {
        model.addAttribute("restaurants", menuRepo.findAllRestaurants());
        model.addAttribute("dishes", menuRepo.findAllDishes(true, null));

        TakeawayOrder takeawayOrder = new TakeawayOrder();
        takeawayOrder.setList(new HashMap<>());
        takeawayOrder.setRestaurant(new Restaurant());
        for(Dish dish: menuRepo.findAllDishes(true, null)) {
            takeawayOrder.getList().put(dish.getId(), null);
        }
        model.addAttribute("takeawayOrder", takeawayOrder);

        return "take-away";
    }

    @PostMapping("/takeaway-confirm")
    public String takeaway_confirm(@ModelAttribute TakeawayOrder takeawayOrder, BindingResult result, RedirectAttributes ra) {
        if(session.getAttribute("logged") != null && session.getAttribute("logged").equals(true)) {
            if (result.hasErrors()) {
                ra.addFlashAttribute("message", "request denied, check input");
            } else {
                Takeaway takeaway = new Takeaway();
                takeaway.setRestaurant(takeawayOrder.getRestaurant());
                takeaway.setOrder(new ArrayList<>());
                takeaway.setLogin(new Login());
                takeaway.getLogin().setTlf_type(session.getAttribute("auth_type").toString());

                for (Entry<Integer, Integer> entry : takeawayOrder.getList().entrySet()) {
                    for(int i = 0; i<entry.getValue(); i++) {
                        Dish dish = new Dish();
                        dish.setId(entry.getKey());
                        takeaway.getOrder().add(dish);
                    }
                }
                ordersRepo.addTakeaway(takeaway);
                ra.addFlashAttribute("message", "take-away accepted");
            }
            return "redirect:/take-away";
        } else {
            ra.addFlashAttribute("message", "log in before reserving");
            return "redirect:/login";
        }
    }

    @GetMapping("/my-orders")
    public String profile(Model model){
        if(session.getAttribute("logged") != null && session.getAttribute("logged").equals(true)) {
            Login login = new Login();
            login.setTlf_type(session.getAttribute("auth_type").toString());
            model.addAttribute("reservations", ordersRepo.findAllReservations(null, login));
            model.addAttribute("takeaways", ordersRepo.findAllTakeaways(null, login));

            return "my-orders/all-orders";
        } else {
            return "error/403";
        }
    }

    @GetMapping("/my-orders/{directory}/view/{id}")
    public String viewOrder(@PathVariable(name = "directory") String directory, @PathVariable(name = "id") int id, Model model){
        if(session.getAttribute("logged") != null && session.getAttribute("logged").equals(true)) {

            Login login = new Login();

            switch (directory) {
                case ("takeaways"):
                    login.setTlf_type(String.valueOf(session.getAttribute("auth_type").toString()));
                    Takeaway takeaway = ordersRepo.findTakeaway(id, login);
                    takeaway.setOrder(ordersRepo.findTakeawayOrders(takeaway));

                    model.addAttribute("takeaway", takeaway);
                    return "my-orders/my-takeaway";
                case ("reservations"):
                    login.setTlf_type(String.valueOf(session.getAttribute("auth_type").toString()));
                    //TODO view my reservation
                    model.addAttribute("reservation", ordersRepo.findReservation(id, login));
                    return "my-orders/my-reservation";
                default:
                    return "error/404";
            }
        } else {
            return "error/403";
        }
    }

}
