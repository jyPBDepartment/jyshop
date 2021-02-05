
package com.jiyi.modules.activity.service.mapper;

import com.jiyi.common.mapper.CoreMapper;
import com.jiyi.modules.activity.domain.YxUserExtract;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
*
* @date 2020-05-13
*/
@Repository
public interface YxUserExtractMapper extends CoreMapper<YxUserExtract> {
    @Select("select IFNULL(sum(extract_price),0) from yx_user_extract " +
            "where status=1 " +
            "and uid=#{uid}")
    double sumPrice(@Param("uid") Long uid);
}
