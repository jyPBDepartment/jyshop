package com.jiyi.modules.user.service.dto;

import lombok.Data;

import java.util.List;

/**
 * @ClassName BillVo
 *
 * @Date 2019/11/12
 **/
@Data
public class BillOrderDto {
    private String time;
    private Integer count;
    private List<BillOrderRecordDto> child;
}
