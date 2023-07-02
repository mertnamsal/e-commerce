package com.ecommerce.dto.request;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RegisterRequestDto {
    @Size(min = 3, message = "Email must be valid.")
    @Email(message = "Email must be valid.", regexp = "[a-z0-9]((\\.|\\+)?[a-z0-9]){1,}@[a-z]+\\.[a-z]{2,3}")
    @NotBlank(message = "Email must be valid.")
    @NotNull(message = "Email must be valid.")
    private String mail;
    @Size(min = 3, max = 256, message = "Password should have at least 8 characters")
    @NotBlank(message = "Password must be valid.")
    @NotNull(message = "Password must be valid.")
    private String password;
    @NotNull
    @NotBlank
    @Size(min = 2, max = 128)
    private String name;
    @NotNull
    @NotBlank
    @Size(min = 2, max = 128)
    private String surname;

}
