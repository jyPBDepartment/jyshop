
package com.jiyi.modules.user.service;

import com.jiyi.common.service.BaseService;
import com.jiyi.modules.user.domain.YxUserLevel;


/**
 * <p>
 * 用户等级记录表 服务类
 * </p>
 *
 *
 * @since 2019-12-06
 */
public interface YxUserLevelService extends BaseService<YxUserLevel> {


    /**
     * 检查是否能成为会员
     * @param uid 用户id
     */
    boolean setLevelComplete(Long uid);

    //UserLevelInfoDto getUserLevelInfo(int id);

    /**
     * 获取当前用户会员等级返回当前用户等级
     * @param uid uid
     * @param grade 用户级别
     * @return YxUserLevel
     */
    YxUserLevel getUserLevel(Long uid, Integer grade);


}
