
package com.jiyi.modules.mp.service.dto;

import com.jiyi.annotation.Query;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
*
* @date 2020-08-10
*/
@Data
public class YxWechatLiveQueryCriteria{
    @ApiModelProperty(value = "直播间状态  101：直播中，102：未开始，103 已结束，104 禁播，105：暂停，106：异常，107：已过期")
    @Query
    private Integer liveStatus;
}
