package com.jiyi.modules.product.param;

import com.jiyi.common.web.param.QueryParam;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 商品属性详情表 查询参数对象
 * </p>
 *
 *
 * @date 2019-10-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="YxStoreProductAttrResultQueryParam对象", description="商品属性详情表查询参数")
public class YxStoreProductAttrResultQueryParam extends QueryParam {
    private static final long serialVersionUID = 1L;
}
