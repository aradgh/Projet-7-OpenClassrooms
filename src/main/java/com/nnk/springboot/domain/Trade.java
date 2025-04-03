package com.nnk.springboot.domain;

import jakarta.persistence.*;
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

    @Column(nullable = false)
    private String account;

    @Column(nullable = false)
    private String type;

    private double buyQuantity;
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
