package com.jiyi.modules.user.service.dto;

import lombok.Data;

/**
 * @ClassName BillVo
 *
 * @Date 2019/11/12
 **/
@Data
public class BillOrderRecordDto {
    private String orderId;
    private String time;
    private Double number;
    private String avatar;
    private String nickname;
}
