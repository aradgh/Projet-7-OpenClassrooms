package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.security.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Trade {
    public Trade(String tradeAccount, String type) {
        this.account = tradeAccount;
        this.type = type;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int tradeId;

    @NotBlank(message = "Le champ 'account' ne doit pas être vide.")
    private String account;

    @NotBlank(message = "Le champ 'type' ne doit pas être vide.")
    private String type;

/*  Ici on remplace le type primitif double par l'objet wrapper Double
 *   afin de pouvoir utiliser l'annotation de validation @NotNull. Cela
 *   permet à Spring de gérer correctement l'absence de valeur (nullabilité + validation) */
    @NotNull
    @Digits(integer = 5, fraction = 2,
    message = "Le champ 'buyQuantity' doit être un nombre avec au maximum 5 chiffres avant la virgule et 2 après")
    private Double buyQuantity;

/*    Les champs suivants n'ont pas d'annotations de validation
    car ils ne sont utilisés nulle part dans le projet */
    private double sellQuantity;
    private double buyPrice;
    private double sellPrice;
    private Timestamp tradeDate;
    private String security;
    private String status;
    private String trader;
    private String benchmark;
    private String book;
    private String creationName;
    private Timestamp creationDate;
    private String revisionName;
    private Timestamp revisionDate;
    private String dealName;
    private String dealType;
    private String sourceListId;
    private String side;

}
