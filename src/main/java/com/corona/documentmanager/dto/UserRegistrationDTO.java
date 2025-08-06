package com.corona.documentmanager.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
public class UserRegistrationDTO {

    @NotEmpty(message = "L'email è obbligatoria")
    @Email(message = "Inserisci un indirizzo email valido")
    private String username;

    @NotEmpty(message = "La password è obbligatoria")
    @Size(min = 8, message = "La password deve contenere almeno 8 caratteri")
    private String password;

    @NotEmpty(message = "La conferma password è obbligatoria")
    private String confirmPassword;
}
