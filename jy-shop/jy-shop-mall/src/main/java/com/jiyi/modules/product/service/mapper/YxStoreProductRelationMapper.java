
package com.jiyi.modules.product.service.mapper;


import com.jiyi.common.mapper.CoreMapper;
import com.jiyi.modules.product.domain.YxStoreProductRelation;
import com.jiyi.modules.product.vo.YxStoreProductRelationQueryVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 商品点赞和收藏表 Mapper 接口
 * </p>
 *
 *
 * @since 2019-10-23
 */
@Repository
public interface YxStoreProductRelationMapper extends CoreMapper<YxStoreProductRelation> {

    @Select("select B.id pid,A.type as category,B.store_name as storeName,B.price," +
            "B.ot_price as otPrice,B.sales,B.image,B.is_show as isShow" +
            " from yx_store_product_relation A left join yx_store_product B " +
            "on A.product_id = B.id where A.type=#{type} and A.uid=#{uid} and A.is_del = 0 and B.is_del = 0 order by A.create_time desc")
    List<YxStoreProductRelationQueryVo> selectRelationList(Page page, @Param("uid") Long uid, @Param("type") String type);


}
