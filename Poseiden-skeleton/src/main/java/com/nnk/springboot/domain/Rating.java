package com.nnk.springboot.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Table(name = "rating")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Size(max = 4)
    Integer id;
    @Size(max = 125)
    String moodysRating;
    @Size(max = 125)
    String sandpRating;
    @Size(max = 125)
    String fitchRating;
    Integer orderNumber;

    public Rating(String moodysRating, String sandPRating, String fitchRating, int i) {
    }
}
