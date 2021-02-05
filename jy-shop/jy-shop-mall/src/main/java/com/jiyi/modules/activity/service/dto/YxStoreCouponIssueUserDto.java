
package com.jiyi.modules.activity.service.dto;

import lombok.Data;

import java.io.Serializable;

/**
*
* @date 2020-05-13
*/
@Data
public class YxStoreCouponIssueUserDto implements Serializable {

    private Integer id;

    /** 领取优惠券用户ID */
    private Integer uid;

    /** 优惠券前台领取ID */
    private Integer issueCouponId;

    /** 领取时间 */
    private Integer addTime;
}
