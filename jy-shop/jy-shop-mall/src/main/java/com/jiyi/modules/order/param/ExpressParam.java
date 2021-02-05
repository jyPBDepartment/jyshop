
package com.jiyi.modules.order.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName ExpressParam
 *
 * @Date 2019/12/9
 **/
@Data
public class ExpressParam implements Serializable {

    @ApiModelProperty(value = "订单编号")
    private String orderCode;

    @ApiModelProperty(value = "快递公司编码")
    private String shipperCode;

    @ApiModelProperty(value = "物流单号")
    private String logisticCode;
}
