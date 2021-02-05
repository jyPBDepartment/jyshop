
package com.jiyi.modules.mp.service.mapper;

import com.jiyi.common.mapper.CoreMapper;
import com.jiyi.modules.mp.domain.YxArticle;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
*
* @date 2020-05-12
*/
@Repository
public interface ArticleMapper extends CoreMapper<YxArticle> {
    @Update("update yx_article set visit=visit+1 " +
            "where id=#{id}")
    int incVisitNum(@Param("id") int id);

}
