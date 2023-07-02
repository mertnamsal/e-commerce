package com.ecommerce.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductAddRequestDto {
    private String productName;
    private int stock;
    private String barcode;
}
