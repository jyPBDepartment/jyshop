
package com.jiyi.modules.user.service.dto;

import com.jiyi.annotation.Query;
import lombok.Data;

/**
*
* @date 2020-05-12
*/
@Data
public class YxUserBillQueryCriteria{
    private String nickname;
    private String category;
    private String type;
    @Query(type = Query.Type.EQUAL)
    private Integer pm;
    @Query(type = Query.Type.EQUAL)
    private String title;
    private String startTime;

    private String endTime;
}
