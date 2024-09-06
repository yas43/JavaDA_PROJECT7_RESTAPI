package com.nnk.springboot.domain.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Integer id;
    @NotBlank(message = "Username is mandatory")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{1,8}$" )
    private String username;
    @NotBlank(message = "FullName is mandatory")
    private String fullname;
    @NotBlank(message = "Password is mandatory")
    @Pattern(regexp = "^\\d+$")
    @Size(min = 8)
    private String password;
    @NotBlank(message = "Role is mandatory")
    private String role;
}
