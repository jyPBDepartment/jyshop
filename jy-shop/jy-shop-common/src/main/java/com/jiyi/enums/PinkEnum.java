
package com.jiyi.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * 拼团相关枚举
 */
@Getter
@AllArgsConstructor
public enum PinkEnum {

	IS_OK_0(0,"未完成"),
	IS_OK_1(1,"已完成"),

	PINK_BOOL_0(0,"未成功，进行中"),
	PINK_BOOL_1(1,"已成功"),
	PINK_BOOL_MINUS_1(-1,"拼团失败"),

	USER_BOOL_0(0,"不在团内"),
	USER_BOOL_1(1,"在团内");

	private Integer value;
	private String desc;




}
