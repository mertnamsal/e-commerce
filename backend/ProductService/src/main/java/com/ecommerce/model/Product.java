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
public class Product extends BaseEntity{
    private String productName;
    private int stock;
    private String barcode;

}
