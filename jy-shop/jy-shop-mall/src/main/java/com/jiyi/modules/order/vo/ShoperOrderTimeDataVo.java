package com.jiyi.modules.order.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName OrderTimeDataDTO
 *
 * @Date 2019/11/25
 **/
@Data
public class ShoperOrderTimeDataVo implements Serializable {

    /**今日成交额*/
    private Double todayPrice;

    /**今日订单数*/
    private Integer todayCount;

    /**昨日成交额*/
    private Double proPrice;

    /**昨日订单数*/
    private Integer proCount;

    /**本月成交额*/
    private Double monthPrice;

    /**本月订单数*/
    private Integer monthCount;

    /**上周订单数*/
    private Integer lastWeekCount;

    /**上周成交额*/
    private Double lastWeekPrice;
}
