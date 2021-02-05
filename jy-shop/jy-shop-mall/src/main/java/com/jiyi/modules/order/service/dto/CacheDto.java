package com.jiyi.modules.order.service.dto;

import com.jiyi.modules.cart.vo.YxStoreCartQueryVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName CacheDto
 *
 * @Date 2019/10/27
 **/
@Data
public class CacheDto implements Serializable {
    private List<YxStoreCartQueryVo> cartInfo;
    private PriceGroupDto priceGroup;
    private OtherDto other;
}
