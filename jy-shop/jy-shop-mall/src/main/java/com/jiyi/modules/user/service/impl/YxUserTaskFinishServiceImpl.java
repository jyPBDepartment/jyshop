
package com.jiyi.modules.user.service.impl;


import com.jiyi.common.service.impl.BaseServiceImpl;
import com.jiyi.modules.user.domain.YxUserTaskFinish;
import com.jiyi.modules.user.service.YxUserTaskFinishService;
import com.jiyi.modules.user.service.mapper.YxUserTaskFinishMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * <p>
 * 用户任务完成记录表 服务实现类
 * </p>
 *
 *
 * @since 2019-12-07
 */
@Slf4j
@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class YxUserTaskFinishServiceImpl extends BaseServiceImpl<YxUserTaskFinishMapper, YxUserTaskFinish> implements YxUserTaskFinishService {

    private final YxUserTaskFinishMapper yxUserTaskFinishMapper;


    /**
     * 设置任务完成
     * @param uid uid
     * @param taskId 任务id
     */
    @Override
    public void setFinish(Long uid, int taskId) {
        int count = this.lambdaQuery()
                .eq(YxUserTaskFinish::getUid,uid)
                .eq(YxUserTaskFinish::getTaskId,taskId)
                .count();
        if(count == 0){
            YxUserTaskFinish userTaskFinish = new YxUserTaskFinish();
            userTaskFinish.setUid(uid);
            userTaskFinish.setTaskId(taskId);
            yxUserTaskFinishMapper.insert(userTaskFinish);
        }
    }



}
