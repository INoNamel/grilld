package com.grilled;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
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
                        model.addAttribute(directory, menuRepo.findAllDishes(true, null));
                        return "admin/show-dishes";
                    }
                case ("clients"):
                    if(session.getAttribute("auth_type").equals("admin")) {
                        model.addAttribute(directory, clientsRepo.findAllClients());
                        return "admin/show-clients";
                    }
                case ("orders"):
                    if(session.getAttribute("auth_type").equals("employee")) {
                        model.addAttribute(directory, ordersRepo.findAllOrders());
                        return "admin/show-orders";
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
                case ("orders"):
                    ordersRepo.deleteOrder(Integer.parseInt(id));
                    return "redirect:/admin/orders";
                default:
                    return "redirect:/";
            }
        } else {
            return "redirect:/admin";
        }
    }
}
