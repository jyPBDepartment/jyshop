
package com.jiyi.modules.shop.service.dto;

import com.jiyi.annotation.Query;
import lombok.Data;

/**
*
* @date 2020-05-12
*/
@Data
public class YxSystemGroupDataQueryCriteria{

    // 精确
    @Query
    private String groupName;
}
