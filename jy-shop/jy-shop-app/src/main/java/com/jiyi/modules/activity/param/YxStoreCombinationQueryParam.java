package com.jiyi.modules.activity.param;

import com.jiyi.common.web.param.QueryParam;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 拼团产品表 查询参数对象
 * </p>
 *
 *
 * @date 2019-11-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="拼团产品表查询参数", description="拼团产品表查询参数")
public class YxStoreCombinationQueryParam extends QueryParam {
    private static final long serialVersionUID = 1L;
}
