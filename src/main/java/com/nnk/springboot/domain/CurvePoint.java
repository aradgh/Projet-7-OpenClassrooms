package com.nnk.springboot.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CurvePoint {
    public CurvePoint(int curveId, BigDecimal term, BigDecimal value) {
        this.curveId = curveId;
        this.term = term;
        this.value = value;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

/*  Ici on remplace le type primitif integer par l'objet wrapper Integer
 *   afin de pouvoir utiliser l'annotation de validation @NotNull. Cela
 *   permet à Spring de gérer correctement l'absence de valeur (nullabilité + validation) */
    @NotNull(message = "Le champ 'curveId' est requis")
    private Integer curveId;

    @NotNull(message = "Le champ 'term' est requis")
    @Digits(integer = 5, fraction = 2,
        message = "Le champ 'term' doit être un nombre avec au maximum 5 chiffres avant la virgule et 2 après")
    private BigDecimal term;

    @NotNull(message = "Le champ 'value' est requis")
    @Digits(integer = 5, fraction = 2,
        message = "Le champ 'value' doit être un nombre avec au maximum 5 chiffres avant la virgule et 2 après")
    private BigDecimal value;


    /*    Les champs suivants n'ont pas d'annotations de validation
    car ils ne sont utilisés nulle part dans le projet */
    private Timestamp asOfDate;
    private Timestamp creationDate;


}
