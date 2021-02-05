
package com.jiyi.modules.system.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.jiyi.domain.BaseDomain;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
*
* @date 2020-05-14
*/
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("job")
public class Job extends BaseDomain {

    /** 岗位ID */
    @TableId
    private Long id;

    /** 岗位名称 */
    @NotBlank(message = "岗位名称不能为空")
    private String name;


    /** 岗位状态 */
    private Boolean enabled;

    @TableField(exist = false)
    private Dept dept;

    /** 岗位排序 */
    private Long sort;


    /** 部门ID */
    private Long deptId;




    public void copy(Job source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
