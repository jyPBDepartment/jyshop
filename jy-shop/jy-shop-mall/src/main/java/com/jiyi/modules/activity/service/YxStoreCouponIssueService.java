
package com.jiyi.modules.activity.service;

import com.jiyi.common.service.BaseService;
import com.jiyi.modules.activity.domain.YxStoreCouponIssue;
import com.jiyi.modules.activity.service.dto.YxStoreCouponIssueDto;
import com.jiyi.modules.activity.service.dto.YxStoreCouponIssueQueryCriteria;
import com.jiyi.modules.activity.vo.YxStoreCouponIssueQueryVo;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
*
* @date 2020-05-13
*/
public interface YxStoreCouponIssueService  extends BaseService<YxStoreCouponIssue>{

    /**
     * 领取优惠券
     * @param id id 优惠券id
     * @param uid uid
     */
    void issueUserCoupon(Integer id, Long uid);

    /**
     * 优惠券列表
     * @param page page
     * @param limit limit
     * @param uid  用户id
     * @return list
     */
    List<YxStoreCouponIssueQueryVo> getCouponList(int page, int limit, Long uid,Long productId,Integer type);

    //int couponCount(int id, int uid);

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(YxStoreCouponIssueQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<YxStoreCouponIssueDto>
    */
    List<YxStoreCouponIssue> queryAll(YxStoreCouponIssueQueryCriteria criteria);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<YxStoreCouponIssueDto> all, HttpServletResponse response) throws IOException;
}
