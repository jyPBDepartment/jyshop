package com.jiyi.modules.user.service.dto;

import lombok.Data;

/**
 * @ClassName PromUserDto
 *
 * @Date 2019/11/12
 **/
@Data
public class PromUserDto {
    private String avatar;
    private String  nickname;
    private Integer childCount;
    private Integer numberCount;
    private Integer  orderCount;
    private Integer uid;
    private String time;
}
