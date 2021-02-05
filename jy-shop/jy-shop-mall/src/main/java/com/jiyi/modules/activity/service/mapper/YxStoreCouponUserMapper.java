
package com.jiyi.modules.activity.service.mapper;

import com.jiyi.common.mapper.CoreMapper;
import com.jiyi.modules.activity.domain.YxStoreCouponUser;
import com.jiyi.modules.activity.vo.StoreCouponUserVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
*
* @date 2020-05-13
*/
@Repository
public interface YxStoreCouponUserMapper extends CoreMapper<YxStoreCouponUser> {
    @Select("select A.id,A.coupon_title as couponTitle,A.coupon_price as couponPrice," +
            "A.end_time as endTime,B.use_min_price as useMinPrice,B.type," +
            "B.product_id as productId" +
            " from yx_store_coupon_user A left join yx_store_coupon B " +
            "on A.cid = B.id " +
            "where A.status = 0" +
            " AND A.end_time > #{now}  " +
            " AND A.uid = #{uid}  AND A.use_min_price <= #{price} " +
            " ORDER BY B.id DESC")
    List<StoreCouponUserVo> selectCouponList(@Param("now") Date now, @Param("price") double price,
                                             @Param("uid") Long uid);
}
