package com.nnk.springboot.domain.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingDTO {
    Integer id;
    @Size(max = 125)
    String moodysRating;
    @Size(max = 125)
    String sandPRating;
    @Size(max = 125)
    String fitchRating;
    @NumberFormat
    Integer order;
}
