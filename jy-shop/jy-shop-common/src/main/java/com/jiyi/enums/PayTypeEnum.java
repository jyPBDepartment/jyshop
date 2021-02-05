
package com.jiyi.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

/**
 *
 * 支付相关枚举
 */
@Getter
@AllArgsConstructor
public enum PayTypeEnum {

	ALI("alipay","支付宝支付"),
	WEIXIN("weixin","微信支付"),
	YUE("yue","余额支付");


	private String value;
	private String desc;

	public static PayTypeEnum toType(String value) {
		return Stream.of(PayTypeEnum.values())
				.filter(p -> p.value.equals(value))
				.findAny()
				.orElse(null);
	}


}
