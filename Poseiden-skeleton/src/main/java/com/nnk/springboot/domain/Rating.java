package com.nnk.springboot.domain;


import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "rating")
public class Rating {
    @Id
    Long id;
    // TODO: Map columns in data table RATING with corresponding java fields
}
