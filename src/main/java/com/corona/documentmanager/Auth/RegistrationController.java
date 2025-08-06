package com.corona.documentmanager.Auth;

import com.corona.documentmanager.dto.UserRegistrationDTO;
import com.corona.documentmanager.exception.UserAlreadyExistsException;
import com.corona.documentmanager.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.corona.documentmanager.user.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.LinkedHashSet;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserRegistrationDTO());
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(@Valid @ModelAttribute("user") UserRegistrationDTO userDto,
                               BindingResult result,
                               Model model) {

        if (result.hasErrors()) {
            return "registration";
        }

        if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            result.rejectValue("confirmPassword", "error.user",
                    "Le password non coincidono");
            return "registration";
        }

        try {
            // Modifica qui - usa il builder della tua classe User
            User newUser = User.builder()
                    .username(userDto.getUsername())
                    .password(passwordEncoder.encode(userDto.getPassword()))
                    .role("USER")
                    .documentComments(new LinkedHashSet<>())  // Inizializza le collezioni
                    .documents(new LinkedHashSet<>())         // Inizializza le collezioni
                    .build();

            userService.createUser(newUser);

            model.addAttribute("successMessage",
                    "Registrazione completata con successo! Ora puoi effettuare il login.");
            return "redirect:/login?registered";

        } catch (UserAlreadyExistsException e) {
            model.addAttribute("errorMessage",
                    "Username già in uso. Scegli un altro username.");
            return "registration";
        }
    }
}

