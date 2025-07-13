package com.nnk.springboot.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class BidList {
    public BidList(String accountTest, String typeTest, double v) {
        this.account = accountTest;
        this.type = typeTest;
        this.bidQuantity = v;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int bidListId;

    @NotBlank(message = "Le champ 'account' ne doit pas être vide")
    private String account;

    @NotBlank(message = "Le champ 'type' ne doit pas être vide")
    private String type;

/*  Ici on remplace le type primitif double par l'objet wrapper Double
*   afin de pouvoir utiliser l'annotation de validation @NotNull. Cela
*   permet à Spring de gérer correctement l'absence de valeur (nullabilité + validation) */
    @NotNull(message = "Le champ 'bidQuantity' est requis")
    @Digits(integer = 5, fraction = 2,
        message = "Le champ 'bidQuantity' doit être un nombre avec au maximum 5 chiffres avant la virgule et 2 après")
    private Double bidQuantity;

/*    Les champs suivants n'ont pas d'annotations de validation
    car ils ne sont utilisés nulle part dans le projet */
    private double bid;
    private double ask;
    private String benchmark;
    private Timestamp bidListDate;
    private String commentary;
    private String security;
    private String status;
    private String trader;
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
