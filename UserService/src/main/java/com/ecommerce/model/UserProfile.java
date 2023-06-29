package com.ecommerce.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserProfile extends BaseEntity{
    @NotNull
    @NotBlank
    private Long authid;
    @NotNull
    @NotBlank
    @Size(min = 2, max = 128)
    private String name;
    @NotNull
    @NotBlank
    @Size(min = 2, max = 128)
    private String surname;

}
