package com.corona.documentmanager.Auth;

import com.corona.documentmanager.dto.UserRegistrationDTO;
import com.corona.documentmanager.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
            User user = User.builder()
                    .username(userDto.getUsername())
                    .password(passwordEncoder.encode(userDto.getPassword()))
                    .role("USER")
                    .build();

            userService.registerNewUser(user);

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

