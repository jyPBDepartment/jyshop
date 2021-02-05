
package com.jiyi.modules.activity.service.dto;

import com.jiyi.annotation.Query;
import lombok.Data;

/**
*
* @date 2020-05-13
*/
@Data
public class YxStoreSeckillQueryCriteria{


    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String title;
}
