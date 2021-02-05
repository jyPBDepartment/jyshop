
package com.jiyi.modules.system.service.dto;

import com.jiyi.annotation.Query;
import lombok.Data;

/**
*
* @date 2020-05-14
*/
@Data
public class DictQueryCriteria{

    @Query(blurry = "name,remark")
    private String blurry;
}
