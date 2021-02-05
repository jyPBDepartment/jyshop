
package com.jiyi.modules.mp.listener;


import com.jiyi.enums.PayTypeEnum;
import com.jiyi.event.TemplateBean;
import com.jiyi.event.TemplateEvent;
import com.jiyi.event.TemplateListenEnum;
import com.jiyi.modules.mp.service.WeiXinSubscribeService;
import com.jiyi.modules.mp.service.WeixinPayService;
import com.jiyi.modules.mp.service.WeixinTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 *
 * 异步监听模板通知事件
 */
@Slf4j
@Component
public class TemplateListener implements SmartApplicationListener {

	@Autowired
	private WeixinTemplateService weixinTemplateService;
	@Autowired
	private WeixinPayService weixinPayService;
	@Autowired
	private WeiXinSubscribeService weiXinSubscribeService;

	@Override
	public boolean supportsEventType(Class<? extends ApplicationEvent> aClass) {
		return aClass == TemplateEvent.class;
	}

	@Async
	@Override
	public void onApplicationEvent(ApplicationEvent applicationEvent) {
		//转换事件类型
		TemplateEvent templateEvent = (TemplateEvent) applicationEvent;
		//获取注册用户对象信息
		TemplateBean templateBean = templateEvent.getTemplateBean();
		log.info("模板事件类型：{}",templateBean.getTemplateType());
		switch (TemplateListenEnum.toType(templateBean.getTemplateType())){
			case TYPE_1:
				weixinTemplateService.paySuccessNotice(templateBean.getOrderId()
						,templateBean.getPrice(),templateBean.getUid());
				weiXinSubscribeService.paySuccessNotice(templateBean.getOrderId()
						,templateBean.getPrice(),templateBean.getUid());
				break;
			case TYPE_2:
				//处理退款与消息
				if(PayTypeEnum.WEIXIN.getValue().equals(templateBean.getPayType())){
					BigDecimal bigDecimal = new BigDecimal("100");
					int payPrice = bigDecimal.multiply(new BigDecimal(templateBean.getPrice())).intValue();
					weixinPayService.refundOrder(templateBean.getOrderId(),payPrice);
				}

				weixinTemplateService.refundSuccessNotice(templateBean.getOrderId(),templateBean.getPrice(),
						templateBean.getUid(),templateBean.getTime());
				break;
			case TYPE_3:
				weixinTemplateService.deliverySuccessNotice(templateBean.getOrderId(),templateBean.getDeliveryName(),
						templateBean.getDeliveryId(),templateBean.getUid());
				break;
			case TYPE_4:
				weixinTemplateService.rechargeSuccessNotice(templateBean.getTime(),templateBean.getPrice(),
						templateBean.getUid());
				break;
			default:
				//todo
		}


	}
}
