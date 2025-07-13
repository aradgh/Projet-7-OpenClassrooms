package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "Le champ 'name' est requis")
    private String name;

    @NotBlank(message = "Le champ 'description' est requis")
    private String description;

    @NotBlank(message = "Le champ 'json' est requis")
    private String json;

    @NotBlank(message = "Le champ 'template' est requis")
    private String template;

    @NotBlank(message = "Le champ 'sqlStr' est requis")
    private String sqlStr;

    @NotBlank(message = "Le champ 'sqlPart' est requis")
    private String sqlPart;

}
