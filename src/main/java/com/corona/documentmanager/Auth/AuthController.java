package com.corona.documentmanager.Auth;

import com.corona.documentmanager.document.Document;
import com.corona.documentmanager.exception.UserNotFoundException;
import com.corona.documentmanager.service.DocumentService;
import com.corona.documentmanager.user.User;
import com.corona.documentmanager.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class AuthController
{
    private final DocumentService documentService;
    private final UserService userService;

    public AuthController(DocumentService documentService, UserService userService) {
        this.documentService = documentService;
        this.userService = userService;
    }


    @GetMapping("/login")
    public String showLoginForm(Model model) {
        return "login";
    }

    @GetMapping("/upload")
    public String showUploadForm(Model model) {
        model.addAttribute("document", "new Document()");
        return "upload";  // Corrisponde al nome del file HTML in templates/upload.html
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login?logout";
    }

    @GetMapping("/user/registration")
    public String showRegistrationForm(Model model) {
        User userDto = new User();
        model.addAttribute("user", userDto);
        return "registration";
    }

    @GetMapping("/home")
    public String showHomeForm(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        System.out.println("=====> username: " + username);
        User currentUser = userService.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Utente non trovato"));

        System.out.println("=====> users: " + currentUser.getUsername());
        List<Document> userDocuments = documentService.findDocumentsByUser(currentUser);

       model.addAttribute("documents", userDocuments);
        model.addAttribute("user", currentUser);

        return "index";
    }

}
