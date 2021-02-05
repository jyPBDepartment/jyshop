package com.jiyi.modules.user.service.dto;


import com.jiyi.modules.user.vo.YxSystemUserLevelQueryVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName UserLevelDto
 *
 * @Date 2019/12/6
 **/
@Data
public class UserLevelDto implements Serializable {
    private List<YxSystemUserLevelQueryVo> list;
    private TaskDto task;
}
