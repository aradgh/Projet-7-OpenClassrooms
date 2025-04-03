package com.nnk.springboot.domain;

import jakarta.persistence.*;

@Entity
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String moodysRating;
    private String sandPRating;
    private String fitchRating;
    private int orderNumber;
}
