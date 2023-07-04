package com.ecommerce.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Vendor extends BaseEntity{
    private Long authid;
    private String name;
    private String surname;
    private Long salesQuantity;


}
