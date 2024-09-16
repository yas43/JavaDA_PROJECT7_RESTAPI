package com.nnk.springboot.domain.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurvePointDTO {
    Integer id;
    @NumberFormat
    Double term;
    @NumberFormat
    Double value;
}
