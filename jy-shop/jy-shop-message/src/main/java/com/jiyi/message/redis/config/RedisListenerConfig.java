
package com.jiyi.message.redis.config;


import cn.hutool.core.util.StrUtil;
import com.jiyi.message.redis.listener.RedisKeyExpirationListener;
import com.jiyi.modules.activity.service.YxStorePinkService;
import com.jiyi.modules.order.service.YxStoreOrderService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

/**
 * redis监听配置
 *
 * @since 2020-02-27
 */

@Configuration
@AllArgsConstructor
public class RedisListenerConfig {

	private final RedisTemplate<String, String> redisTemplate;
	private final RedisConfigProperties redisConfigProperties;
	private final YxStoreOrderService storeOrderService;
	private final YxStorePinkService storePinkService;

	@Bean
    RedisMessageListenerContainer container(RedisConnectionFactory factory) {
		String topic =StrUtil.format("__keyevent@{}__:expired", redisConfigProperties.getDatabase());
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(factory);
		container.addMessageListener(new RedisKeyExpirationListener(redisTemplate,redisConfigProperties
		 ,storeOrderService,storePinkService), new PatternTopic(topic));
		return container;
	}
}

