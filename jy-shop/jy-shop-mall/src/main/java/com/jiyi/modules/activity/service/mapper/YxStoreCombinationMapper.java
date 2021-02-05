
package com.jiyi.modules.activity.service.mapper;

import com.jiyi.common.mapper.CoreMapper;
import com.jiyi.modules.activity.domain.YxStoreCombination;
import com.jiyi.modules.activity.vo.YxStoreCombinationQueryVo;
import com.jiyi.modules.product.vo.YxStoreProductQueryVo;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
*
* @date 2020-05-13
*/
@Repository
public interface YxStoreCombinationMapper extends CoreMapper<YxStoreCombination> {

    @Select("SELECT c.id,c.effective_time as effectiveTime,c.info,c.image,c.people,c.price, s.sales as sales," +
            "c.title,c.unit_name as unitName,s.price as productPrice FROM yx_store_combination c " +
            "INNER JOIN yx_store_product s ON s.id=c.product_id " +
            " WHERE c.is_show = 1 AND c.is_del = 0 AND c.start_time < now() " +
            " AND c.stop_time > now() ORDER BY c.sort desc,c.id desc")
    List<YxStoreCombinationQueryVo> getCombList(Page page);

    @Override
    IPage<YxStoreCombination> selectPage(IPage<YxStoreCombination> page, @Param("ew") Wrapper<YxStoreCombination> queryWrapper);

    @Select("SELECT c.id,c.effective_time as effectiveTime,c.image,c.people,c.price,c.browse," +
            "c.description,c.image,c.images,c.info," +
            "c.product_id as productId,c.sales,c.start_time as startTime" +
            ",c.stock,c.stop_time stopTime," +
            "c.title,c.unit_name as unitName,s.price as productPrice FROM yx_store_combination c " +
            "INNER JOIN yx_store_product s ON s.id=c.product_id " +
            " WHERE c.is_show = 1 AND c.is_del = 0 AND c.id = #{id} ")
    YxStoreCombinationQueryVo getCombDetail(Long id);

    @Select("SELECT c.id,c.image,c.price,c.title as storeName,c.is_show as isShow,c.cost," +
            "c.sales,c.stock,c.is_del as isDel" +
            " FROM yx_store_combination c " +
            " WHERE c.id = #{id} and c.is_del = 0 ")
    YxStoreProductQueryVo combinatiionInfo(Long id);
}
