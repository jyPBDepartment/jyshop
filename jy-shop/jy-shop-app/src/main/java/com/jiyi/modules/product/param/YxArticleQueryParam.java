package com.jiyi.modules.product.param;

import com.jiyi.common.web.param.QueryParam;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 文章管理表 查询参数对象
 * </p>
 *
 *
 * @date 2019-10-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="YxArticleQueryParam对象", description="文章管理表查询参数")
public class YxArticleQueryParam extends QueryParam {
    private static final long serialVersionUID = 1L;
}
