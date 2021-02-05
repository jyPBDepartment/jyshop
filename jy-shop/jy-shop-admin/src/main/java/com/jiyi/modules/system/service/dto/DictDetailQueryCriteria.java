
package com.jiyi.modules.system.service.dto;

import com.jiyi.annotation.Query;
import lombok.Data;

/**
*
* @date 2020-05-14
*/
@Data
public class DictDetailQueryCriteria{

    @Query(type = Query.Type.INNER_LIKE)
    private String label;

    private String dictName;
}
