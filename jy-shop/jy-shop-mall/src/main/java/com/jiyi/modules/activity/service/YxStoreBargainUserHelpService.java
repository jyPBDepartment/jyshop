
package com.jiyi.modules.activity.service;


import com.jiyi.common.service.BaseService;
import com.jiyi.modules.activity.domain.YxStoreBargainUserHelp;
import com.jiyi.modules.activity.vo.YxStoreBargainUserHelpQueryVo;

import java.util.List;

/**
 * <p>
 * 砍价用户帮助表 服务类
 * </p>
 *
 *
 * @since 2019-12-21
 */
public interface YxStoreBargainUserHelpService extends BaseService<YxStoreBargainUserHelp> {

    /**
     * 获取砍价帮
     * @param bargainId 砍价商品id
     * @param bargainUserUid 砍价用户id
     * @param page page
     * @param limit limit
     * @return list
     */
    List<YxStoreBargainUserHelpQueryVo> getList(Long bargainId, Long bargainUserUid, int page, int limit);

    /**
     * 获取砍价帮总人数
     * @param bargainId 砍价产品ID
     * @param bargainUserUid 用户参与砍价表id
     * @return int
     */
    int getBargainUserHelpPeopleCount(Long bargainId,Long bargainUserUid);


}
