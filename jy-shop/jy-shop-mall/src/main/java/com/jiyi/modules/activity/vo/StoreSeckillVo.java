package com.jiyi.modules.activity.vo;


import com.jiyi.modules.product.domain.YxStoreProductAttrValue;
import com.jiyi.modules.product.vo.YxStoreProductAttrQueryVo;
import com.jiyi.modules.product.vo.YxStoreProductReplyQueryVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 秒杀产品表 查询结果对象
 * </p>
 *
 *
 * @date 2019-12-17
 */
@Data
@Builder
public class StoreSeckillVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "秒杀产品评论信息")
    private YxStoreProductReplyQueryVo reply;

    @ApiModelProperty(value = "秒杀产品评论数量")
    private Integer replyCount;

    @ApiModelProperty(value = "秒杀产品信息")
    private YxStoreSeckillQueryVo storeInfo;

    @Builder.Default
    @ApiModelProperty(value = "秒杀产品用户是否收藏")
    private Boolean userCollect = false;

    @ApiModelProperty(value = "模板名称")
    private String tempName;

    private List<YxStoreProductAttrQueryVo> productAttr = new ArrayList();

    private Map<String, YxStoreProductAttrValue> productValue = new LinkedHashMap<>();

}
