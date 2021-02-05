
package com.jiyi.modules.order.service.dto;

import lombok.Data;

import java.io.Serializable;

/**
*
* @date 2020-05-12
*/
@Data
public class YxStoreOrderCartInfoDto implements Serializable {

    private Integer id;

    /** 订单id */
    private Integer oid;

    /** 购物车id */
    private Integer cartId;

    /** 商品ID */
    private Integer productId;

    /** 购买东西的详细信息 */
    private String cartInfo;

    /** 唯一id */
    private String unique;
}
