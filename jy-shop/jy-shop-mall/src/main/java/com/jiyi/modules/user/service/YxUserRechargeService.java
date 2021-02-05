
package com.jiyi.modules.user.service;

import com.jiyi.common.service.BaseService;
import com.jiyi.modules.user.domain.YxUser;
import com.jiyi.modules.user.domain.YxUserRecharge;
import com.jiyi.modules.user.service.dto.YxUserRechargeDto;
import com.jiyi.modules.user.service.dto.YxUserRechargeQueryCriteria;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
*
* @date 2020-05-12
*/
public interface YxUserRechargeService  extends BaseService<YxUserRecharge>{


    void updateRecharge(YxUserRecharge userRecharge);

    YxUserRecharge getInfoByOrderId(String orderId);

    /**
     * 添加充值记录
     * @param user 用户
     * @param price 充值金额
     * @param paidPrice 赠送金额
     */
    String addRecharge(YxUser user, String price, String paidPrice);

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(YxUserRechargeQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<YxUserRechargeDto>
    */
    List<YxUserRecharge> queryAll(YxUserRechargeQueryCriteria criteria);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<YxUserRechargeDto> all, HttpServletResponse response) throws IOException;
}
