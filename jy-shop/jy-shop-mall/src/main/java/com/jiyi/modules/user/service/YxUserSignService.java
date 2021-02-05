
package com.jiyi.modules.user.service;



import com.jiyi.common.service.BaseService;
import com.jiyi.modules.user.domain.YxUser;
import com.jiyi.modules.user.domain.YxUserSign;
import com.jiyi.modules.user.vo.SignVo;
import com.jiyi.modules.user.vo.YxUserQueryVo;

import java.util.List;

/**
 * <p>
 * 签到记录表 服务类
 * </p>
 *
 *
 * @since 2019-12-05
 */
public interface YxUserSignService extends BaseService<YxUserSign> {

    /**
     *
     * @param yxUser 用户
     * @return 签到积分
     */
    int sign(YxUser yxUser);

    /**
     * 分页获取用户签到数据
     * @param uid 用户id
     * @param page  page
     * @param limit limit
     * @return list
     */
    List<SignVo> getSignList(Long uid, int page, int limit);

    //boolean getYesterDayIsSign(int uid);

    //boolean getToDayIsSign(int uid);

    //int getSignSumDay(int uid);

    /**
     * 获取签到用户信息
     * @param yxUser  yxUser
     * @return YxUserQueryVo
     */
    YxUserQueryVo userSignInfo(YxUser yxUser);


}
