package com.grilled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private HttpSession session;

    @Autowired
    private LoginRepository loginRepo;


    @GetMapping("/logout")
    public String logout() {
        session.removeAttribute("logged");
        session.removeAttribute("auth_type");
        session.removeAttribute("auth_name");
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(Login login, Model model) {
        if(session.getAttribute("logged") == null) {
            model.addAttribute("loginForm", login);
            return "login";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/login")
    public String login(@ModelAttribute Login login, BindingResult result, RedirectAttributes ra) {
        if(!result.hasErrors() && loginRepo.verifyPass(login)) {
            session.setAttribute("logged", true);
            session.setAttribute("auth_type", login.getTlf_type());
            session.setAttribute("auth_name", login.getName());
            if(login.getTlf_type().equals("admin")) {
                return "redirect:/admin/dishes";
            } else if(login.getTlf_type().equals("employee")){
                return "redirect:/admin/take-aways";
            } else {
                return "redirect:/";
            }
        } else {
            ra.addFlashAttribute("message", "wrong login credentials");
            return "redirect:/login";
        }
    }

    @GetMapping("/signup")
    public String signup(Login login, Model model) {
        if(session.getAttribute("logged") == null) {
            model.addAttribute("loginForm", login);
            return "signup";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute Login login, BindingResult result, RedirectAttributes ra) {
        if(!loginRepo.verifySingUp(login) && !result.hasErrors()) {
            if(!loginRepo.verifyPass(login)) {
                loginRepo.signUp(login);
                ra.addFlashAttribute("message", "new profile added");
                return "redirect:/login";
            } else {
                ra.addFlashAttribute("message", "phone number already occupied");
                return "redirect:/signup";
            }
        } else {
            ra.addFlashAttribute("message", "invalid input");
            return "redirect:/signup";
        }
    }
}
