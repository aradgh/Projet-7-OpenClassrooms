package com.nnk.springboot.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class RuleName {
    public RuleName(String ruleName, String description, String json, String template, String sql, String sqlPart) {
        this.name = ruleName;
        this.description = description;
        this.json = json;
        this.template = template;
        this.sqlStr = sql;
        this.sqlPart = sqlPart;
    }
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
