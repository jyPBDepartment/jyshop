
package com.jiyi.modules.product.service.mapper;

import com.jiyi.common.mapper.CoreMapper;
import com.jiyi.modules.product.domain.YxStoreProductAttrValue;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
*
* @date 2020-05-12
*/
@Repository
public interface StoreProductAttrValueMapper extends CoreMapper<YxStoreProductAttrValue> {


    /**
     * 正常商品 加库存 减销量
     * @param num
     * @param productId
     * @param unique
     * @return
     */
    @Update("update yx_store_product_attr_value set stock=stock+#{num}, sales=sales-#{num}" +
            " where product_id=#{productId} and `unique`=#{unique}")
    int incStockDecSales(@Param("num") int num,@Param("productId") Long productId,
                                    @Param("unique")  String unique);


    /**
     * 拼团商品 加库存 减销量
     * @param num
     * @param productId
     * @param unique
     * @return
     */

    @Update("update yx_store_product_attr_value set stock=stock+#{num}, pink_stock=pink_stock+#{num}, sales=sales-#{num}" +
            " where product_id=#{productId} and `unique`=#{unique}")
    int incCombinationStockDecSales(@Param("num") int num,@Param("productId") Long productId,
                         @Param("unique")  String unique);


    /**
     * 秒杀 加库存 减销量
     * @param num
     * @param productId
     * @param unique
     * @return
     */
    @Update("update yx_store_product_attr_value set stock=stock+#{num},seckill_stock=seckill_stock+#{num}, sales=sales-#{num}" +
            " where product_id=#{productId} and `unique`=#{unique}")
    int incSeckillStockDecSales(@Param("num") int num,@Param("productId") Long productId,
                                    @Param("unique")  String unique);


    /**
     * 普通商品 减库存 加销量
     * @param num
     * @param productId
     * @param unique
     * @return
     */
    @Update("update yx_store_product_attr_value set stock=stock-#{num}, sales=sales+#{num}" +
            " where product_id=#{productId} and `unique`=#{unique} and stock >= #{num}")
    int decStockIncSales(@Param("num") int num, @Param("productId") Long productId,
                         @Param("unique")  String unique);

    /**
     * 拼团产品 减库存 加销量
     * @param num
     * @param productId
     * @param unique
     * @return
     */
    @Update("update yx_store_product_attr_value set stock=stock-#{num}, pink_stock=pink_stock-#{num} ,sales=sales+#{num}" +
            " where product_id=#{productId} and `unique`=#{unique} and stock >= #{num} and pink_stock>=#{num}")
    int decCombinationStockIncSales(int num, Long productId, String unique);

    /**
     * 秒杀产品 减库存 加销量
     * @param num
     * @param productId
     * @param unique
     * @return
     */
    @Update("update yx_store_product_attr_value set stock=stock-#{num}, seckill_stock=seckill_stock-#{num},sales=sales+#{num}" +
            " where product_id=#{productId} and `unique`=#{unique} and stock >= #{num} and seckill_stock>=#{num}")
    int decSeckillStockIncSales(int num, Long productId, String unique);
}
