package com.nnk.springboot.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Table(name = "rulename")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RuleName {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    @Size(max = 125)
    String name;
    @Size(max = 125)
    String description;
    @Size(max = 125)
    String json;
    @Size(max = 512)
    String template;
    @Size(max = 125)
    String sqlStr;
    @Size(max = 125)
    String sqlPart;

    public RuleName(String ruleName, String description, String json, String template, String sql, String sqlPart) {
    }
}
