package com.jiyi.modules.order.service.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName StatusDto
 *
 * @Date 2019/10/30
 **/
@Data
public class StatusDto implements Serializable {
    private String _class;
    private String _msg;
    private String _payType;
    private String _title;
    private String _type;
}
