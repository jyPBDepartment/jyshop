
package com.jiyi.modules.product.service.mapper;

import com.jiyi.common.mapper.CoreMapper;
import com.jiyi.modules.product.domain.YxStoreProduct;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
*
* @date 2020-05-12
*/
@Repository
public interface StoreProductMapper extends CoreMapper<YxStoreProduct> {

    /**
     * 正常商品库存 减库存 加销量
     * @param num
     * @param productId
     * @return
     */
    @Update("update yx_store_product set stock=stock-#{num}, sales=sales+#{num}" +
            " where id=#{productId} and stock >= #{num}")
    int decStockIncSales(@Param("num") int num,@Param("productId") Long productId);

    /**
     * 正常商品库存 加库存 减销量
     * @param num
     * @param productId
     * @return
     */
    @Update("update yx_store_product set stock=stock+#{num}, sales=sales-#{num}" +
            " where id=#{productId}")
    int incStockDecSales(@Param("num") int num,@Param("productId") Long productId);


    @Update("update yx_store_product set is_show = #{status} where id = #{id}")
    void updateOnsale(@Param("status") Integer status, @Param("id") Long id);

    /**
     * 拼团商品库存，减库存 加销量
     * @param num
     * @param productId
     * @param activityId
     * @return
     */
    @Update("update yx_store_combination set stock=stock-#{num}, sales=sales+#{num}" +
            " where id=#{activityId} and stock >= #{num}")
    int decCombinationStockIncSales(int num, Long productId,Long activityId);

    /**
     * 秒杀产品库存 减库存，加销量
     * @param num
     * @param productId
     * @param activityId
     * @return
     */
    @Update("update yx_store_seckill set stock=stock-#{num}, sales=sales+#{num}" +
            " where id=#{activityId} and stock >= #{num}")
    int decSeckillStockIncSales(int num, Long productId,Long activityId);

    /**
     * 拼团商品库存，加库存 减销量
     * @param num
     * @param productId
     * @param activityId
     */
    @Update("update yx_store_combination set stock=stock+#{num}, sales=sales-#{num}" +
            " where id=#{activityId} and stock >= #{num}")
    void incCombinationStockIncSales(Integer num, Long productId, Long activityId);

    /**
     * 秒杀产品库存 加库存，减销量
     * @param num
     * @param productId
     * @param activityId
     * @return
     */
    @Update("update yx_store_seckill set stock=stock+#{num}, sales=sales-#{num}" +
            " where id=#{activityId} and stock >= #{num}")
    void incSeckillStockIncSales(Integer num, Long productId, Long activityId);

    /**
     * 商品浏览量
     * @param productId
     * @return
     */
    @Update("update yx_store_product set browse=browse+1 " +
            "where id=#{productId}")
    int incBrowseNum(@Param("productId") Long productId);

    @Delete("DELETE from yx_system_attachment where name like CONCAT(#{id},'_%',#{name}, '%')")
    void deleteForwardImg(@Param("id") Long id,@Param("name") String name);
}
