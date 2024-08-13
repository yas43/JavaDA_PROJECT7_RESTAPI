package com.nnk.springboot.domain;

import jakarta.persistence.*;
import org.hibernate.validator.constraints.Length;


import java.sql.Timestamp;


@Entity
@Table(name = "curvepoint")
public class CurvePoint {
    @Id
    Long id;
    // TODO: Map columns in data table CURVEPOINT with corresponding java fields
}
