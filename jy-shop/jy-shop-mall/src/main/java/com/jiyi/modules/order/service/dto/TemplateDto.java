package com.jiyi.modules.order.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @ClassName TemplateDTO
 *
 * @Date 2020/5/28
 **/
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TemplateDto {
    private Double number;
    private BigDecimal price;
    private Double first;
    private BigDecimal firstPrice;
    private Double _continue;
    private BigDecimal continuePrice;
    private Integer tempId;
    private Integer cityId;
}
