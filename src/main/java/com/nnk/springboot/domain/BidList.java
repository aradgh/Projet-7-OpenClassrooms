package com.nnk.springboot.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class BidList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int bidListId;

    @Column(nullable = false)
    private String account;

    @Column(nullable = false)
    private String type;

    private double bidQuantity;
    private double askQuantity;
    private double bid;
    private double ask;
    private String benchmark;
    private LocalDateTime bidListDate;
    private String commentary;
    private String security;
    private String status;
    private String trader;
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
