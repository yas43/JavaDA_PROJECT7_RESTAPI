package com.nnk.springboot.domain.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Integer id;
    @NotBlank(message = "Username is mandatory")
    @NotEmpty(message = "username mandatory")
    @Size(min = 8)
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,20}$",
            message ="password should contain at least a capital letter ,a number and a symbol" )
    private String username;
    @NotBlank(message = "FullName is mandatory")
    private String fullname;
    @NotBlank(message = "Password is mandatory")
    @Pattern(regexp = "^\\d+$",message = "at least 8 numbers")
    @Size(min = 8)
    @NotEmpty(message = "password mandatory")
    private String password;
    @NotBlank(message = "Role is mandatory")
    private String role;
}
