package com.corona.documentmanager.user;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@RestController
public class UserController {
    private final UserRepository repository;

    UserController(UserRepository repository) {
        this.repository = repository;
    }
//    @PostMapping("/perform_login")
//    @ResponseBody
//    public String login(@RequestParam String username, @RequestParam String password) {
//        UsernamePasswordAuthenticationToken authReq
//                = new UsernamePasswordAuthenticationToken(username, password);
//        System.out.println("usernameccccc" + username);
//        System.out.println("password" + password);
////        Authentication auth = authManager.authenticate(authReq);
////
////        SecurityContext sc = SecurityContextHolder.getContext();
////        sc.setAuthentication(auth);
////        session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, sc);
//        return "login";
//    }
    private String getErrorMessage(HttpServletRequest request, String key) {
        Exception exception = (Exception) request.getSession().getAttribute(key);
        String error = "";
        if (exception instanceof BadCredentialsException) {
            error = "Invalid username and password!";
        } else if (exception instanceof LockedException) {
            error = exception.getMessage();
        } else {
            error = "Invalid username and password!";
        }
        return error;
    }
}
