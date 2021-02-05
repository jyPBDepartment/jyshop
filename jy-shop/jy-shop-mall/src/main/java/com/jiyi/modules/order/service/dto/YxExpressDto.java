
package com.jiyi.modules.order.service.dto;

import lombok.Data;

import java.io.Serializable;

/**
*
* @date 2020-05-12
*/
@Data
public class YxExpressDto implements Serializable {

    /** 快递公司id */
    private Integer id;

    /** 快递公司简称 */
    private String code;

    /** 快递公司全称 */
    private String name;

    /** 排序 */
    private Integer sort;

    /** 是否显示 */
    private Integer isShow;
}
