package com.jiyi.modules.order.vo;

import com.jiyi.modules.activity.vo.StoreCouponUserVo;
import com.jiyi.modules.cart.vo.YxStoreCartQueryVo;
import com.jiyi.modules.order.service.dto.PriceGroupDto;
import com.jiyi.modules.product.vo.YxSystemStoreQueryVo;
import com.jiyi.modules.user.domain.YxUserAddress;
import com.jiyi.modules.user.vo.YxUserQueryVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName ConfirmOrderVo
 *
 * @Date 2019/10/27
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConfirmOrderVo implements Serializable {
    //地址信息
    private YxUserAddress addressInfo;

    //砍价id
    private Integer bargainId;

    private List<YxStoreCartQueryVo> cartInfo;

    private Integer combinationId;

    //优惠券减
    private Boolean deduction;

    private Boolean enableIntegral;

    private Double enableIntegralNum;

    //积分抵扣
    private Integer integralRatio;

    private String orderKey;

    private PriceGroupDto priceGroup;

    private Integer seckillId;

    //店铺自提
    private Integer storeSelfMention;

    //店铺信息
    private YxSystemStoreQueryVo systemStore;


    private StoreCouponUserVo usableCoupon;

    private YxUserQueryVo userInfo;



}
