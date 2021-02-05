
package com.jiyi.modules.activity.service.mapper;

import com.jiyi.common.mapper.CoreMapper;
import com.jiyi.modules.activity.domain.YxStoreSeckill;
import com.jiyi.modules.product.vo.YxStoreProductQueryVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
*
* @date 2020-05-13
*/
@Repository
public interface YxStoreSeckillMapper extends CoreMapper<YxStoreSeckill> {

    @Select("SELECT c.id,c.image,c.price,c.title as storeName,c.is_show as isShow,c.cost," +
            "c.is_postage as isPostage,c.postage,c.sales,c.stock,c.is_del as isDel" +
            " FROM yx_store_seckill c " +
            " WHERE c.id = #{id} and c.is_del = 0 ")
    YxStoreProductQueryVo seckillInfo(Long id);
}
