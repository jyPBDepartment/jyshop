package com.jiyi.modules.product.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName AttrValueDTO
 *
 * @Date 2019/10/23
 **/
@Data
public class AttrValueDto {

    @ApiModelProperty(value = "属性")
    private String attr;

    @ApiModelProperty(value = "是否选择")
    private Boolean check = false;
}
