package com.jiyi.modules.activity.vo;


import com.jiyi.modules.user.vo.YxUserQueryVo;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName BargainVo
 *
 * @Date 2019/12/21
 **/
@Data
@Builder
public class BargainVo implements Serializable {
    private YxStoreBargainQueryVo bargain;
    private YxUserQueryVo userInfo;
    private Integer bargainSumCount;//砍价支付成功订单数量
}
