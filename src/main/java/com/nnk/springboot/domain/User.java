package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Username is mandatory")
    private String username;

    @NotBlank(message = "Password is mandatory")
    @Pattern(
        regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[^\\w\\s]).{8,}$",
        message = "Le mot de passe doit contenir au moins 8 caractères, une lettre majuscule, un chiffre et un symbole"
    )
    private String password;

    @NotBlank(message = "Full Name is mandatory")
    private String fullname;

    @NotBlank(message = "Role is mandatory")
    private String role;
}
