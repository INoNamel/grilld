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
        return "error/405";
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public String handleIllegalArgumentException(IllegalArgumentException ex) {
        return "error/405";
    }

    @ExceptionHandler({NumberFormatException.class})
    public String handleTypeMismatch(NumberFormatException ex) {
        return "error/405";
    }

    @ExceptionHandler({DateTimeException.class})
    public String handleDateTimeException(DateTimeException ex) {
        return "error/405";
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
        model.addAttribute("takeawayForm", new Takeaway());
        return "take-away";
    }

    @PostMapping("/takeaway-confirm")
    public String takeaway_confirm(@ModelAttribute Takeaway takeaway, BindingResult result, RedirectAttributes ra) {
        if(session.getAttribute("logged") != null && session.getAttribute("logged").equals(true)) {
            //TODO Take-Away

            ra.addFlashAttribute("message", "take-away accepted");
            return "redirect:/take-away";
        } else {
            ra.addFlashAttribute("message", "log in before reserving");
            return "redirect:/login";
        }
    }

}
