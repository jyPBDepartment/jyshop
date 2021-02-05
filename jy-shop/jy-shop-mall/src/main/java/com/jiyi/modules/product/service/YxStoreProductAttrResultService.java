
package com.jiyi.modules.product.service;

import com.jiyi.common.service.BaseService;
import com.jiyi.modules.product.domain.YxStoreProductAttrResult;

import java.util.Map;


/**
*
* @date 2020-05-12
*/
public interface YxStoreProductAttrResultService  extends BaseService<YxStoreProductAttrResult>{

    /**
     * 新增商品属性详情
     * @param map map
     * @param productId 商品id
     */
    void insertYxStoreProductAttrResult(Map<String, Object> map, Long productId);
}
