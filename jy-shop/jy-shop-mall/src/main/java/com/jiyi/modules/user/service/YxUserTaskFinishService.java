
package com.jiyi.modules.user.service;


import com.jiyi.common.service.BaseService;
import com.jiyi.modules.user.domain.YxUserTaskFinish;

/**
 * <p>
 * 用户任务完成记录表 服务类
 * </p>
 *
 *
 * @since 2019-12-07
 */
public interface YxUserTaskFinishService extends BaseService<YxUserTaskFinish> {

    /**
     * 设置任务完成
     * @param uid uid
     * @param taskId 任务id
     */
    void setFinish(Long uid,int taskId);


}
