package com.nnk.springboot.domain;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class CurvePoint {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int curveId;
    private LocalDateTime asOfDate;
    private double term;
    private double value;
    private LocalDateTime creationDate;


}
