
package com.jiyi.modules.mp.utils;


import com.jiyi.modules.user.service.dto.WechatUserDto;
import com.alibaba.fastjson.JSONObject;

/**
 * @ClassName YshopUtils
 *
 * @Date 2020/6/27
 **/
public class YshopUtils {
    public static WechatUserDto getWechtUser(String str) {
        return JSONObject.parseObject(str,WechatUserDto.class);
    }
}
