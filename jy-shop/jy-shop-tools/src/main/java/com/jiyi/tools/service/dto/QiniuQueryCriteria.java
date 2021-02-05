
package com.jiyi.tools.service.dto;

import com.jiyi.annotation.Query;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @date 2019-6-4 09:54:37
 */
@Data
public class QiniuQueryCriteria{

    @Query(type = Query.Type.INNER_LIKE)
    private String name;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createTime;
}
