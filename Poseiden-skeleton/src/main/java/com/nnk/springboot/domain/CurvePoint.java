package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;


import java.sql.Timestamp;


@Entity
@Table(name = "curvepoint")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CurvePoint {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    Integer curveId;
    Timestamp asofDate;
    Double term;
    Double value;
    Timestamp creationDate;



}
