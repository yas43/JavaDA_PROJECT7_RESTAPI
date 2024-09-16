package com.nnk.springboot.domain.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BidListDTO {
    Integer id;
    @NotBlank
    @Size(max = 30)
    String account;
    @NotBlank
    @Size(max = 30)
    String type;
    @NumberFormat
    Double bidQuantity;

}
