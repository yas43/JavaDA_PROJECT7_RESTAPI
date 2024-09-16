package com.nnk.springboot.domain.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TradeDTO {
    Integer tradeId;
    @NotBlank
    @Size(max = 30)
    String account;
    @NotBlank
    @Size(max = 30)
    String type;
    @NumberFormat
    Double buyQuantity;
}
