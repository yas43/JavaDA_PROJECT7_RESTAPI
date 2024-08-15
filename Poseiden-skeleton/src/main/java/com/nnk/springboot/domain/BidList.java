package com.nnk.springboot.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.NotBlank;
import jdk.jfr.*;
import lombok.*;
import org.hibernate.validator.constraints.*;

import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "bidlist")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BidList {

    @Id
    @GeneratedValue(strategy =GenerationType.AUTO)
    @Column(name = "bidlistid")
    Integer bidListId;
    @NotBlank
    @Size(max = 30)
    String account;
    @NotBlank
    @Size(max = 30)
    String type;
    Double bidQuantity;
    Double askQuantity;
    Double bid;
    Double ask;
    @Size(max = 125)
    String benchmark;
    Timestamp bidListDate;
    @Size(max = 125)
    String commentary;
    @Size(max = 125)
    String security;
    @Size(max = 10)
    String status;
    @Size(max = 125)
    String trader;
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


    public BidList(String accountTest, String typeTest, double v) {
    }
}
