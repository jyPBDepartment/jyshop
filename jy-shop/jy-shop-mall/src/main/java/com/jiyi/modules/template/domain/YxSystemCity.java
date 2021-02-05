
package com.jiyi.modules.template.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
*
* @date 2020-06-29
*/
@Data
@TableName("yx_system_city")
public class YxSystemCity implements Serializable {

    @TableId
    private Integer id;


    /** 城市id */
    @NotNull
    @JsonProperty(value = "city_id")
    private Integer cityId;


    /** 省市级别 */
    @NotNull
    private Integer level;


    /** 父级id */
    @NotNull
    private Integer parentId;


    /** 区号 */
    @NotBlank
    private String areaCode;


    /** 名称 */
    @NotBlank
    private String name;


    /** 合并名称 */
    @NotBlank
    private String mergerName;


    /** 经度 */
    @NotBlank
    private String lng;


    /** 纬度 */
    @NotBlank
    private String lat;


    /** 是否展示 */
    @NotNull
    private Integer isShow;

    @TableField(exist = false)
    private List<YxSystemCity> children;


    public void copy(YxSystemCity source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
