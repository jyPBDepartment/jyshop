
package com.jiyi.modules.category.service.dto;

import com.jiyi.annotation.Query;
import lombok.Data;

/**
*
* @date 2020-05-12
*/
@Data
public class YxStoreCategoryQueryCriteria{
    @Query
    private String cateName;
}
