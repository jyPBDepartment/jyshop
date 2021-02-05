package com.jiyi.modules.mp.vo;

import com.jiyi.modules.mp.service.dto.YxWechatLiveDto;
import lombok.Data;

import java.util.List;

@Data
public class WechatLiveVo {

    private List<YxWechatLiveDto> content;

    private Long totalElements;

    private Integer pageNumber;

    private Integer lastPage;


}
