
package com.jiyi.modules.quartz.service.dto;

import com.jiyi.annotation.Query;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
*
* @date 2020-05-13
*/
@Data
public class QuartzJobQueryCriteria{

    @Query(type = Query.Type.INNER_LIKE)
    private String jobName;

    @Query
    private Boolean isSuccess;

    @Query
    private Boolean isPause;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createTime;
}
