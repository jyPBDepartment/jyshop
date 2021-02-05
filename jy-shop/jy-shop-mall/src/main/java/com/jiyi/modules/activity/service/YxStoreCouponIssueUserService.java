
package com.jiyi.modules.activity.service;

import com.jiyi.common.service.BaseService;
import com.jiyi.modules.activity.domain.YxStoreCouponIssueUser;
import com.jiyi.modules.activity.service.dto.YxStoreCouponIssueUserDto;
import com.jiyi.modules.activity.service.dto.YxStoreCouponIssueUserQueryCriteria;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
*
* @date 2020-05-13
*/
public interface YxStoreCouponIssueUserService  extends BaseService<YxStoreCouponIssueUser>{

    /**
     * 添加优惠券领取记录
     * @param uid 用户id
     * @param id 前台优惠券id
     */
    void addUserIssue(Long uid, Integer id);

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(YxStoreCouponIssueUserQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<YxStoreCouponIssueUserDto>
    */
    List<YxStoreCouponIssueUser> queryAll(YxStoreCouponIssueUserQueryCriteria criteria);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<YxStoreCouponIssueUserDto> all, HttpServletResponse response) throws IOException;
}
