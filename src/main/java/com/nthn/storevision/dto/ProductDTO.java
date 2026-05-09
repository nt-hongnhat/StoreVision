package com.nthn.storevision.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
    private Long id;

    @NotBlank(message = "{product.name.required}")
    private String name;

    @NotNull(message = "{product.price.required}")
    @Min(value = 0, message = "{product.price.min}")
    private BigDecimal price;

    @NotNull(message = "{product.stock.required}")
    @Min(value = 0, message = "{product.stock.min}")
    private Integer stockQuantity;
}
