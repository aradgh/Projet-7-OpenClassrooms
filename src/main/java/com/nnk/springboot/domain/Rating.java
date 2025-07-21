package com.nnk.springboot.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Rating {
    public Rating(String moodysRating, String sandPRating, String fitchRating, int orderNumber) {
        this.moodysRating = moodysRating;
        this.sandPRating = sandPRating;
        this.fitchRating = fitchRating;
        this.orderNumber = orderNumber;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "La notation Moody's ne doit pas être vide")
    private String moodysRating;

    @NotBlank(message = "La notation S&P ne doit pas être vide")
    private String sandPRating;

    @NotBlank(message = "La notation Fitch ne doit pas être vide")
    private String fitchRating;

/*  Ici on remplace le type primitif integer par l'objet wrapper Integer
 *   afin de pouvoir utiliser l'annotation de validation @NotNull. Cela
 *   permet à Spring de gérer correctement l'absence de valeur (nullabilité + validation) */
    @NotNull(message = "L'ordre ne doit pas être nul")
    @Min(value = 0, message = "L'ordre doit être positif ou nul")
    private Integer orderNumber;

}
