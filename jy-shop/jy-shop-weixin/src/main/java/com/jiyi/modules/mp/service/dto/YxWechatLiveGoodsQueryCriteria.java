
package com.jiyi.modules.mp.service.dto;

import com.jiyi.annotation.Query;
import lombok.Data;

/**
*
* @date 2020-08-11
*/
@Data
public class YxWechatLiveGoodsQueryCriteria{

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private String name;
}
