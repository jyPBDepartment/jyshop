
package com.jiyi.tools.express.config;


import com.jiyi.constant.ShopConstants;
import com.jiyi.enums.ShopCommonEnum;
import com.jiyi.tools.express.ExpressService;
import com.jiyi.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExpressAutoConfiguration {


    private static RedisUtils redisUtil;

    @Autowired
    public ExpressAutoConfiguration(RedisUtils redisUtil) {
        ExpressAutoConfiguration.redisUtil = redisUtil;
    }

    public static ExpressService expressService() {
        ExpressService expressService = (ExpressService)redisUtil.get(ShopConstants.YSHOP_EXPRESS_SERVICE);
        if(expressService != null) {
            return expressService;
        }

        ExpressProperties properties = new ExpressProperties();
        String enable = redisUtil.getY("exp_enable");
        String appId = redisUtil.getY("exp_appId");
        String appKey = redisUtil.getY("exp_appKey");
        properties.setAppId(appId);
        properties.setAppKey(appKey);

        if(ShopCommonEnum.ENABLE_2.getValue().toString().equals(enable)){
            properties.setEnable(false);
        }else{
            properties.setEnable(true);
        }
        ExpressService service = new ExpressService();
        service.setProperties(properties);
        redisUtil.set(ShopConstants.YSHOP_EXPRESS_SERVICE,service);
        return service;
    }

}
