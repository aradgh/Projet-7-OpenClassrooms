package com.nnk.springboot.domain;

import jakarta.persistence.*;

@Entity
public class RuleName {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;
    private String description;
    private String json;
    private String template;
    private String sqlStr;
    private String sqlPart;
}
