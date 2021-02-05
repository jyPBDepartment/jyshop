package com.jiyi.modules.manage.param;

import com.jiyi.common.web.param.QueryParam;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 快递公司表 查询参数对象
 * </p>
 *
 *
 * @date 2019-12-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="YxExpressQueryParam对象", description="快递公司表查询参数")
public class YxExpressQueryParam extends QueryParam {
    private static final long serialVersionUID = 1L;
}
