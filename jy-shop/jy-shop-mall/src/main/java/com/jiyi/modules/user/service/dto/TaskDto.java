package com.jiyi.modules.user.service.dto;


import com.jiyi.modules.user.vo.YxSystemUserTaskQueryVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName TaskDto
 *
 * @Date 2019/12/6
 **/
@Data
public class TaskDto implements Serializable {
    private List<YxSystemUserTaskQueryVo> list;
    private Integer reachCount;
    private List<YxSystemUserTaskQueryVo> task;
}
