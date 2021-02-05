package com.jiyi.modules.order.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @ClassName OrderVerifyParam
 *
 * @Date 2020/03/05
 **/
@Data
public class OrderVerifyParam implements Serializable {

    @ApiModelProperty(value = "订单核销状态：1确认0正常")
    private Integer isConfirm;

    @NotBlank(message = "缺少核销码")
    @ApiModelProperty(value = "核销码")
    private String verifyCode;

}
