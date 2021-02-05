package com.jiyi.modules.order.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @ClassName OrderCountDTO
 *
 * @Date 2019/10/30
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserOrderCountVo implements Serializable {

    /**订单支付没有退款 数量*/
    @ApiModelProperty(value = "订单支付没有退款数量")
    private Integer orderCount;

    /**订单支付没有退款 支付总金额*/
    @ApiModelProperty(value = "订单支付没有退款支付总金额")
    private Double sumPrice;

    /**订单待支付 数量*/
    @ApiModelProperty(value = "订单待支付数量")
    private Integer unpaidCount;

    /**订单待发货数量*/
    @ApiModelProperty(value = "订单待发货数量")
    private Integer unshippedCount;

    /**订单待收货数量*/
    @ApiModelProperty(value = "订单待收货数量")
    private Integer receivedCount;

    /**订单待评价数量*/
    @ApiModelProperty(value = "订单待评价数量")
    private Integer evaluatedCount;

    /**订单已完成数量*/
    @ApiModelProperty(value = "订单已完成数量")
    private Integer completeCount;

    /**订单退款数量*/
    @ApiModelProperty(value = "订单退款数量")
    private Integer refundCount;
}
