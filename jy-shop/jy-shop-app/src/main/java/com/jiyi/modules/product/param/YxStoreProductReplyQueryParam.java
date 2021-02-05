package com.jiyi.modules.product.param;

import com.jiyi.common.web.param.QueryParam;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 评论表 查询参数对象
 * </p>
 *
 *
 * @date 2019-10-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="YxStoreProductReplyQueryParam对象", description="评论表查询参数")
public class YxStoreProductReplyQueryParam extends QueryParam {
    private static final long serialVersionUID = 1L;
}
