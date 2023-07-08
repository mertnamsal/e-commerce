package com.ecommerce.model;

import com.ecommerce.model.enums.ERole;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Auth extends BaseEntity{

    @NotNull
    @NotBlank
    private String mail;
    @NotNull
    @NotBlank
    private String password;
    @Enumerated(EnumType.STRING)
    private ERole role;


}
