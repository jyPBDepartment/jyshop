
package com.jiyi.modules.user.service.dto;

import lombok.Data;

import java.io.Serializable;


/**
*
* @date 2019-10-06
*/
@Data
public class YxUserSmallDto implements Serializable {

    // 用户id
    private Integer uid;

    // 用户昵称
    private String nickname;

    // 用户头像
    private String avatar;

    // 手机号码
    private String phone;


}
