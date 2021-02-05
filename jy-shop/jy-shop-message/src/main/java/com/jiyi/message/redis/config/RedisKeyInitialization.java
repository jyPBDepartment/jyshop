
package com.jiyi.message.redis.config;

import com.jiyi.modules.shop.domain.YxSystemConfig;
import com.jiyi.modules.shop.service.YxSystemConfigService;
import com.jiyi.utils.RedisUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * api服务启动初始化reids
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RedisKeyInitialization  implements CommandLineRunner {


    private final YxSystemConfigService systemConfigService;


    private final RedisUtils redisUtils;


    /**
     * 初始化redis
     */
    private void redisKeyInitialization(){
        try {
            List<YxSystemConfig> systemConfigs = systemConfigService.list();
            for (YxSystemConfig systemConfig : systemConfigs) {
                redisUtils.set(systemConfig.getMenuName(),systemConfig.getValue());
            }

            log.info("---------------redisKey初始化成功---------------");
        }catch (Exception e){
            log.error("redisKey初始化失败: {}",e.getMessage());
        }

    }

    @Override
    public void run(String... args) throws Exception {
        this.redisKeyInitialization();
    }
}
