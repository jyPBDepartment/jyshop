
package com.jiyi.modules.product.service;

import com.jiyi.common.service.BaseService;
import com.jiyi.modules.product.domain.YxStoreProductAttr;
import com.jiyi.modules.product.domain.YxStoreProductAttrValue;
import com.jiyi.modules.product.service.dto.FromatDetailDto;
import com.jiyi.modules.product.service.dto.ProductFormatDto;

import java.util.List;
import java.util.Map;


/**
*
* @date 2020-05-12
*/
public interface YxStoreProductAttrService  extends BaseService<YxStoreProductAttr>{

    /**
     * 增加库存减去销量
     * @param num 数量
     * @param productId 商品id
     * @param unique sku唯一值
     */
    void incProductAttrStock(Integer num, Long productId, String unique,String type);

    /**
     * 减少库存增加销量
     * @param num 数量
     * @param productId 商品id
     * @param unique sku唯一值
     */
    void decProductAttrStock(int num, Long productId, String unique,String type);


    /**
     * 更加sku 唯一值获取sku对象
     * @param unique 唯一值
     * @return YxStoreProductAttrValue
     */
    YxStoreProductAttrValue uniqueByAttrInfo(String unique);

    /**
     * 获取商品sku属性
     * @param productId 商品id
     * @return map
     */
    Map<String, Object> getProductAttrDetail(long productId);

    /**
     * 新增商品属性
     * @param items attr
     * @param attrs value
     * @param productId 商品id
     */
    void insertYxStoreProductAttr(List<FromatDetailDto> items, List<ProductFormatDto> attrs,
                                  Long productId);
}
