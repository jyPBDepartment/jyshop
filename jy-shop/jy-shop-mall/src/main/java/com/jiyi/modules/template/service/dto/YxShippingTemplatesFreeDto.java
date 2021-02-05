
package com.jiyi.modules.template.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
*
* @date 2020-06-29
*/
@Data
public class YxShippingTemplatesFreeDto implements Serializable {

    /** 编号 */
    private Integer id;

    /** 省ID */
    private Integer provinceId;

    /** 模板ID */
    private Integer tempId;

    /** 城市ID */
    private Integer cityId;

    /** 包邮件数 */
    private BigDecimal number;

    /** 包邮金额 */
    private BigDecimal price;

    /** 计费方式 */
    private Integer type;

    /** 分组唯一值 */
    private String uniqid;
}
