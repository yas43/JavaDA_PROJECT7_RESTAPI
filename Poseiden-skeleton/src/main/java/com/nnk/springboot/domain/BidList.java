package com.nnk.springboot.domain;


import jakarta.persistence.*;

import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "bidlist")
public class BidList {
    @Id
    Long id;
    // TODO: Map columns in data table BIDLIST with corresponding java fields
}
