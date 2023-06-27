package com.ecommerce.rabbitmq.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUser implements Serializable {

    private Long authid;
    private String mail;
    private String name;
    private String surname;
}
