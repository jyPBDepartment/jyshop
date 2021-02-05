package com.jiyi.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @ClassName TemplateBean
 *
 * @Date 2020/6/6
 **/
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TemplateBean {
    private String templateType;
    private String orderId;
    private String time;
    private String price;
    private String deliveryName;
    private String deliveryId;
    private String payType;
    private Long uid;

}
