
package com.jiyi.modules.user.service;

import com.jiyi.common.service.BaseService;
import com.jiyi.modules.shop.domain.YxSystemUserLevel;
import com.jiyi.modules.user.service.dto.UserLevelDto;
import com.jiyi.modules.user.service.dto.YxSystemUserLevelDto;
import com.jiyi.modules.user.service.dto.YxSystemUserLevelQueryCriteria;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
*
* @date 2020-05-12
*/
public interface YxSystemUserLevelService  extends BaseService<YxSystemUserLevel>{


    /**
     * 获取当前的下一个会员等级id
     * @param levelId 等级id
     * @return int
     */
    int getNextLevelId(int levelId);

    //boolean getClear(int levelId);


    /**
     * 获取会员等级列表及其任务列表
     * @return UserLevelDto
     */
    UserLevelDto getLevelInfo(Long uid);

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(YxSystemUserLevelQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<YxSystemUserLevelDto>
    */
    List<YxSystemUserLevel> queryAll(YxSystemUserLevelQueryCriteria criteria);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<YxSystemUserLevelDto> all, HttpServletResponse response) throws IOException;
}
