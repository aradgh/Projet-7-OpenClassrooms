package com.nnk.springboot.domain;

import jakarta.persistence.*;
import lombok.*;

import java.security.Timestamp;

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

    @Column(nullable = false)
    private String account;

    @Column(nullable = false)
    private String type;

//    TODO: Ajouter des annotations pour valider les champs correspondants
    private double bidQuantity;
    private double askQuantity;
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
