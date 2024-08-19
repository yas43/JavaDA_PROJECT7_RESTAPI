package com.nnk.springboot.domain.dto;

import jakarta.validation.constraints.*;
import lombok.*;

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
    Double buyQuantity;
}
