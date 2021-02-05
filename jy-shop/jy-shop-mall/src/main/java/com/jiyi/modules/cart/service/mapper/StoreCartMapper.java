
package com.jiyi.modules.cart.service.mapper;

import com.jiyi.common.mapper.CoreMapper;
import com.jiyi.modules.cart.domain.YxStoreCart;
import com.jiyi.modules.order.service.dto.CountDto;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
*
* @date 2020-05-12
*/
@Repository
public interface StoreCartMapper extends CoreMapper<YxStoreCart> {

    @Select("select IFNULL(sum(cart_num),0) from yx_store_cart " +
            "where is_pay=0 and is_del=0 and is_new=0 and uid=#{uid}")
    int cartSum(@Param("uid") Long uid);


    @Select("SELECT t.cate_name as catename from yx_store_cart c  " +
            "LEFT JOIN yx_store_product p on c.product_id = p.id  " +
            "LEFT JOIN yx_store_category t on p.cate_id = t.id " +
            "WHERE c.is_pay = 1")
    List<CountDto> findCateName();
}
