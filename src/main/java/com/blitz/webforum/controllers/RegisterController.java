/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blitz.webforum.controllers;

import com.blitz.webforum.interfaces.UserInterface;
import com.blitz.webforum.models.User;
import static javax.swing.text.StyleConstants.ModelAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Ghufran Tripa
 */
@Controller
public class RegisterController {

    @Autowired
    private UserInterface userInterface;

    @GetMapping("/register")
    public String index(Model model) {

        User user = new User();
        model.addAttribute("user", user);

        return "register";
    }

    @PostMapping("/register")
    public String store(@ModelAttribute("user") User user, RedirectAttributes ra) throws Exception {

        if (user.getEmail().equals("")) {
            ra.addFlashAttribute("danger", "Email cannot be null!");
            return "redirect:/register";
        }

        if (user.getName().equals("")) {
            ra.addFlashAttribute("danger", "Name cannot be null!");
            return "redirect:/register";
        }

        if (user.getPassword().equals("")) {
            ra.addFlashAttribute("danger", "Password cannot be null!");
            return "redirect:/register";
        }
        
        if (user.getAdress().equals("")) {
            ra.addFlashAttribute("danger", "Adress cannot be null!");
            return "redirect:/register";
        }

        userInterface.register(user);
        return "redirect:/login";
    }


}
