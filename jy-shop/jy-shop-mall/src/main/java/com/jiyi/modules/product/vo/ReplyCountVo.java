package com.jiyi.modules.product.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @ClassName ReplyCount
 *
 * @Date 2019/11/4
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReplyCountVo implements Serializable {

    @ApiModelProperty(value = "总的评论数")
    private Integer sumCount;

    @ApiModelProperty(value = "好评数")
    private Integer goodCount;

    @ApiModelProperty(value = "中评数")
    private Integer inCount;

    @ApiModelProperty(value = "差评数")
    private Integer poorCount;

    @ApiModelProperty(value = "好评率")
    private String replyChance;

    @ApiModelProperty(value = "好评星星数")
    private String replySstar;

}
