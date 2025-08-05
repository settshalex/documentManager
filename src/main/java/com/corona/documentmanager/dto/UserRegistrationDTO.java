package com.corona.documentmanager.dto;

import lombok.Builder;
import lombok.Data;
import javax.persistence.*;

@Data
@Builder
public class UserRegistrationDTO {

    @NotEmpty(message = "Lo username è obbligatorio")
    private String username;

    @NotEmpty(message = "La password è obbligatoria")
    @Size(min = 6, message = "La password deve essere di almeno 6 caratteri")
    private String password;

    @NotEmpty(message = "La conferma password è obbligatoria")
    private String confirmPassword;
}
