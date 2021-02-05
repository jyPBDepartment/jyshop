package com.jiyi.modules.user.dto;

import lombok.Data;

/**
 * @ClassName UserRankDTO
 *
 * @Date 2019/11/13
 **/
@Data
public class UserRankDTO {
    private Integer uid;
    private Integer count;
    private String nickname;
    private String avatar;
}
