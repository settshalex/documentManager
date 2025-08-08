package com.corona.documentmanager.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class AuthProvider implements AuthenticationProvider {
    @Autowired
    private UserService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        try {
            String username = authentication.getName();
            String password = authentication.getCredentials().toString();
            LoggedUser loggingUser = userDetailsService.loadUserByUsername(username);
            if (passwordEncoder.matches(password, loggingUser.getPassword())) {
                return new UsernamePasswordAuthenticationToken(
                        loggingUser, null, loggingUser.getAuthorities());

            } else {
                throw new BadCredentialsException("Email or password not valid");
            }
        } catch (Exception e) {
            throw new BadCredentialsException("Email or password not valid" + e.getMessage());
        }


    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
