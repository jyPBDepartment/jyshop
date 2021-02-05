
package com.jiyi.modules.template.service.dto;

import lombok.Data;

import java.io.Serializable;

/**
*
* @date 2020-06-29
*/
@Data
public class YxSystemCityDto implements Serializable {

    private Integer id;

    /** 城市id */
    private Integer cityId;

    /** 省市级别 */
    private Integer level;

    /** 父级id */
    private Integer parentId;

    /** 区号 */
    private String areaCode;

    /** 名称 */
    private String name;

    /** 合并名称 */
    private String mergerName;

    /** 经度 */
    private String lng;

    /** 纬度 */
    private String lat;

    /** 是否展示 */
    private Integer isShow;
}
