
package com.jiyi.modules.activity.service;

import com.jiyi.common.service.BaseService;
import com.jiyi.modules.activity.domain.YxStoreCouponUser;
import com.jiyi.modules.activity.service.dto.YxStoreCouponUserDto;
import com.jiyi.modules.activity.service.dto.YxStoreCouponUserQueryCriteria;
import com.jiyi.modules.activity.vo.StoreCouponUserVo;
import com.jiyi.modules.activity.vo.YxStoreCouponUserQueryVo;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
*
* @date 2020-05-13
*/
public interface YxStoreCouponUserService  extends BaseService<YxStoreCouponUser>{

    /**
     * 获取当前用户优惠券数量
     * @param uid uid
     * @return int
     */
    int getUserValidCouponCount(Long uid);

    void useCoupon(int id);

    /**
     * 获取用户优惠券
     * @param id 优惠券id
     * @param uid 用户id
     * @return YxStoreCouponUser
     */
    YxStoreCouponUser getCoupon(Integer id,Long uid);


    /**
     * 获取满足条件的可用优惠券
     * @param cartIds 购物车ids
     * @return list
     */
    List<StoreCouponUserVo> beUsableCouponList(Long uid,String cartIds);

    /**
     * 获取下单时候满足的优惠券
     * @param uid uid
     * @param price 总价格
     * @param productIds list
     * @return list
     */
    List<StoreCouponUserVo> getUsableCouponList(Long uid, double price, List<String> productIds);



    /**
     * 获取用户优惠券
     * @param uid uid
     * @return list
     */
    List<YxStoreCouponUserQueryVo> getUserCoupon(Long uid);

    /**
     * 添加优惠券记录
     * @param uid 用户id
     * @param cid 优惠券id
     */
    void addUserCoupon(Long uid,Integer cid);

/**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(YxStoreCouponUserQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<YxStoreCouponUserDto>
    */
    List<YxStoreCouponUser> queryAll(YxStoreCouponUserQueryCriteria criteria);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<YxStoreCouponUserDto> all, HttpServletResponse response) throws IOException;
}
