package com.jiyi.modules.user.param;

import com.jiyi.common.web.param.QueryParam;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 微信用户表 查询参数对象
 * </p>
 *
 *
 * @date 2019-10-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="YxWechatUserQueryParam对象", description="微信用户表查询参数")
public class YxWechatUserQueryParam extends QueryParam {
    private static final long serialVersionUID = 1L;
}
