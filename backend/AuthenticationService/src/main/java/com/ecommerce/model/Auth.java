package com.ecommerce.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Auth extends BaseEntity {

    @NotNull
    @NotBlank
    private String mail;
    @NotNull
    @NotBlank
    private String password;


}
