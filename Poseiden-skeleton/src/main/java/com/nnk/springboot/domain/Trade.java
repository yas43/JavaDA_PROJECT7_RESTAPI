package com.nnk.springboot.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.sql.Timestamp;


@Entity
@Table(name = "trade")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tradeid")
    Integer tradeId;
    @NotBlank
    @Size(max = 30)
    String account;
    @NotBlank
    @Size(max = 30)
    String type;
    Double buyQuantity;
    Double sellQuantity;
    Double buyPrice;
    Double sellPrice;
    Timestamp tradeDate;
    @Size(max = 125)
    String security;
    @Size(max = 10)
    String status;
    @Size(max = 125)
    String trader;
    @Size(max = 125)
    String benchmark;
    @Size(max = 125)
    String book;
    @Size(max = 125)
    String creationName;
    Timestamp creationDate;
    @Size(max = 125)
    String revisionName;

    Timestamp revisionDate;
    @Size(max = 125)
    String dealName;
    @Size(max = 125)
    String dealType;
    @Size(max = 125)
    String sourceListId;
    @Size(max = 125)
    String side;



    public Trade(int i, String account1, String type1, double v, Timestamp timestamp) {
    }
}
