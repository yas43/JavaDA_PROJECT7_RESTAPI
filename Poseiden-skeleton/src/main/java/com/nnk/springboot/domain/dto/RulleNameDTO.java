package com.nnk.springboot.domain.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RulleNameDTO {
    Integer id;
    @Size(max = 125)
    String name;
    @Size(max = 125)
    String description;
    @Size(max = 125)
    String json;
    @Size(max = 512)
    String template;
    @Size(max = 125)
    String sqlStr;
    @Size(max = 125)
    String sqlPart;
}
