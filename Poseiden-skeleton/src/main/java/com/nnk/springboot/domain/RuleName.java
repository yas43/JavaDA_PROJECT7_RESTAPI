package com.nnk.springboot.domain;


import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "rulename")
public class RuleName {
    @Id
    Long id;
    // TODO: Map columns in data table RULENAME with corresponding java fields
}
