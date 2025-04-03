package com.nnk.springboot.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.security.Timestamp;

@Entity
public class CurvePoint {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int curveId;
    private Timestamp asOfDate;
    private double term;
    private double value;
    private Timestamp creationDate;


}
