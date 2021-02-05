
package com.jiyi.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

/**
 *
 * 短信类型枚举
 */
@Getter
@AllArgsConstructor
public enum SmsTypeEnum {
    BIND("bind","绑定手机短信"),
    LOGIN("login","登陆短信"),
    REGISTER("register","注册短信");

    private String value;
    private String desc;

    public static SmsTypeEnum toType(String value) {
        return Stream.of(SmsTypeEnum.values())
                .filter(c -> c.value == value)
                .findAny()
                .orElse(null);
    }
}
