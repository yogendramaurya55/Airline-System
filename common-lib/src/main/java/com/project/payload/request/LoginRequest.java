package com.project.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequest {
    @Email(message = "enter a valid email")
    @NotNull(message = "email should not be empty")
    private String email;

    @NotNull(message ="password should not be null")
    @NotBlank(message = "password should not be blank")
    private String password;
}
