
package com.jiyi.modules.user.service.dto;

import lombok.Data;

/**
 * @ClassName UserBillDTO
 *
 * @Date 2019/12/11
 **/
@Data
public class UserBillDto {
    private Integer pm;
    private String gtitle;
    private String category;
    private String type;
    private Double number;
    private String nickname;
}
