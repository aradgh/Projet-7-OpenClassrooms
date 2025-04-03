package com.nnk.springboot.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int tradeId;

    @Column(nullable = false)
    private String account;

    @Column(nullable = false)
    private String type;

    private double buyQuantity;
    private double sellQuantity;
    private double buyPrice;
    private double sellPrice;
    private LocalDateTime tradeDate;
    private String security;
    private String status;
    private String trader;
    private String benchmark;
    private String book;
    private String creationName;
    private LocalDateTime creationDate;
    private String revisionName;
    private LocalDateTime revisionDate;
    private String dealName;
    private String dealType;
    private String sourceListId;
    private String side;
}
