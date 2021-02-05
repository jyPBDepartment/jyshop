
package com.jiyi.message.rocketmq;

import com.jiyi.enums.OrderInfoEnum;
import com.jiyi.modules.order.domain.YxStoreOrder;
import com.jiyi.modules.order.service.YxStoreOrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQListener;

/**
 * @ClassName 消费者
 *
 * @Date 2020/1/1
 **/
//@Component
//@RocketMQMessageListener(
//        topic = "yshop-topic",
//        consumerGroup = "yshop-group",
//        selectorExpression = "*"
//)
@Slf4j
@AllArgsConstructor
public class MqConsumer implements RocketMQListener<String> {

    private final YxStoreOrderService storeOrderService;

    @Override
    public void onMessage(String msg) {
        log.info("系统开始处理延时任务---订单超时未付款---订单id:" + msg);

        Long id = Long.valueOf(msg);

        YxStoreOrder order = storeOrderService.lambdaQuery()
                .eq(YxStoreOrder::getId, id)
                .eq(YxStoreOrder::getPaid, OrderInfoEnum.PAY_STATUS_0.getValue())
                .one();

        //只有待支付的订单能取消
        if(order != null){
            storeOrderService.cancelOrder(order.getOrderId(),null);
            log.info("订单id:{},未在规定时间支付取消成功",id);
        }

        log.info("=====处理成功======");

    }
}
