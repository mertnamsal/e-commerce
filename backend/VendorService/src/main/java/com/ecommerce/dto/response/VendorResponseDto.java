package com.ecommerce.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VendorResponseDto {
    private String name;
    private String surname;
    private Long salesQuantity;
}
