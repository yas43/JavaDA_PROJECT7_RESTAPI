package com.nnk.springboot.domain.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

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
    Double bidQuantity;

}
