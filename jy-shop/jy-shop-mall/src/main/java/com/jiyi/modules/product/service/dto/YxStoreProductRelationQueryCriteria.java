
package com.jiyi.modules.product.service.dto;

import lombok.Data;
import java.util.List;
import com.jiyi.annotation.Query;

/**
 *
 * @date 2020-09-03
 */
@Data
public class YxStoreProductRelationQueryCriteria{
    @Query(type = Query.Type.EQUAL)
    private String type;
}
