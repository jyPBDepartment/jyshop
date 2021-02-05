package com.jiyi.modules.order.service.dto;

import com.jiyi.serializer.BigDecimalSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName PriceGroup
 *
 * @Date 2019/10/27
 **/
@Data
public class PriceGroupDto {

    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal costPrice;

    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal storeFreePostage;

    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal storePostage;

    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal totalPrice;

    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal vipPrice;

}
