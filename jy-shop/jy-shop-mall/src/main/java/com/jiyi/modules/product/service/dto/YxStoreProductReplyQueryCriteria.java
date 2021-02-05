
package com.jiyi.modules.product.service.dto;

import com.jiyi.annotation.Query;
import lombok.Data;

/**
*
* @date 2020-05-12
*/
@Data
public class YxStoreProductReplyQueryCriteria{
    @Query
    private Integer isDel;
}
