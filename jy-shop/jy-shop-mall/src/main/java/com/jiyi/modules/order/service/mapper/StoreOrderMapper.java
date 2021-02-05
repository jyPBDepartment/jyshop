
package com.jiyi.modules.order.service.mapper;

import com.jiyi.common.mapper.CoreMapper;
import com.jiyi.modules.order.domain.YxStoreOrder;
import com.jiyi.modules.order.service.dto.ChartDataDto;
import com.jiyi.modules.order.vo.OrderDataVo;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
*
* @date 2020-05-12
*/
@Repository
public interface StoreOrderMapper extends CoreMapper<YxStoreOrder> {



    @Select("SELECT sum(pay_price) as price,count(id) as count," +
            "DATE_FORMAT(create_time, '%m-%d') as time FROM yx_store_order" +
            " WHERE is_del = 0 AND paid = 1 AND refund_status = 0 " +
            "GROUP BY DATE_FORMAT(create_time,'%Y-%m-%d') ORDER BY create_time DESC")
    List<OrderDataVo> getOrderDataPriceList(Page page);


    @Select("SELECT IFNULL(sum(pay_price),0) " +
            " FROM yx_store_order ${ew.customSqlSegment}")
    Double todayPrice(@Param(Constants.WRAPPER) Wrapper<YxStoreOrder> wrapper);



    @Select("select IFNULL(sum(pay_price),0) from yx_store_order " +
            "where paid=1 and is_del=0 and refund_status=0 and uid=#{uid}")
    double sumPrice(@Param("uid") Long uid);


    @Select("SELECT COUNT(*) FROM yx_store_order WHERE pay_time >= ${today}")
    Integer countByPayTimeGreaterThanEqual(@Param("today")int today);

    @Select("SELECT COUNT(*) FROM yx_store_order WHERE pay_time < ${today}  and pay_time >= ${yesterday}")
    Integer countByPayTimeLessThanAndPayTimeGreaterThanEqual(@Param("today")int today, @Param("yesterday")int yesterday);

    @Select( "select IFNULL(sum(pay_price),0)  from yx_store_order " +
            "where refund_status=0 and is_del=0 and paid=1")
    Double sumTotalPrice();

    @Select("SELECT IFNULL(sum(pay_price),0) as num," +
            "DATE_FORMAT(create_time, '%m-%d') as time " +
            " FROM yx_store_order where refund_status=0 and is_del=0 and paid=1 and pay_time >= #{time}" +
            " GROUP BY DATE_FORMAT(create_time,'%Y-%m-%d') " +
            " ORDER BY create_time ASC")
    List<ChartDataDto> chartList(@Param("time") Date time);
    @Select("SELECT count(id) as num," +
            "DATE_FORMAT(create_time, '%m-%d') as time " +
            " FROM yx_store_order where refund_status=0 and is_del=0 and paid=1 and pay_time >= #{time}" +
            " GROUP BY DATE_FORMAT(create_time,'%Y-%m-%d') " +
            " ORDER BY create_time ASC")
    List<ChartDataDto> chartListT(@Param("time") Date time);
}
