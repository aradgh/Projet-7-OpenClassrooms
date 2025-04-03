package com.nnk.springboot.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.security.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CurvePoint {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int curveId;
    private Timestamp asOfDate;
    private double term;
    private double value;
    private Timestamp creationDate;


    public CurvePoint(int i, double v, double v1) {
        this.curveId = i;
        this.term = v;
        this.value = v1;
    }
}
