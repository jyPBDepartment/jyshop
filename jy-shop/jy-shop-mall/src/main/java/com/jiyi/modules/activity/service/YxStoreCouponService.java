
package com.jiyi.modules.activity.service;

import com.jiyi.common.service.BaseService;
import com.jiyi.modules.activity.domain.YxStoreCoupon;
import com.jiyi.modules.activity.service.dto.YxStoreCouponDto;
import com.jiyi.modules.activity.service.dto.YxStoreCouponQueryCriteria;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
*
* @date 2020-05-13
*/
public interface YxStoreCouponService  extends BaseService<YxStoreCoupon>{

/**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(YxStoreCouponQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<YxStoreCouponDto>
    */
    List<YxStoreCoupon> queryAll(YxStoreCouponQueryCriteria criteria);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<YxStoreCouponDto> all, HttpServletResponse response) throws IOException;
}
