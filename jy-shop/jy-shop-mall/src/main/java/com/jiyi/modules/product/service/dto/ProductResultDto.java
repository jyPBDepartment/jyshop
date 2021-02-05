package com.jiyi.modules.product.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName 产品结果DTO
 *
 * @Date 2020/4/24
 **/
@Getter
@Setter
@Builder
public class ProductResultDto {
    private Double minPrice;

    private Double minOtPrice;

    private Double minCost;

    private Integer stock;
}
