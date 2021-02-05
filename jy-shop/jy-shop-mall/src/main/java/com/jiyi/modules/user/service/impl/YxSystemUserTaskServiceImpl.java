
package com.jiyi.modules.user.service.impl;

import cn.hutool.core.util.NumberUtil;
import com.jiyi.common.service.impl.BaseServiceImpl;
import com.jiyi.common.utils.QueryHelpPlus;
import com.jiyi.dozer.service.IGenerator;
import com.jiyi.enums.ShopCommonEnum;
import com.jiyi.modules.order.service.mapper.StoreOrderMapper;
import com.jiyi.modules.shop.domain.YxSystemUserLevel;
import com.jiyi.modules.user.domain.YxSystemUserTask;
import com.jiyi.modules.user.domain.YxUserTaskFinish;
import com.jiyi.modules.user.service.YxSystemUserLevelService;
import com.jiyi.modules.user.service.YxSystemUserTaskService;
import com.jiyi.modules.user.service.YxUserBillService;
import com.jiyi.modules.user.service.YxUserTaskFinishService;
import com.jiyi.modules.user.service.dto.TaskDto;
import com.jiyi.modules.user.service.dto.YxSystemUserTaskDto;
import com.jiyi.modules.user.service.dto.YxSystemUserTaskQueryCriteria;
import com.jiyi.modules.user.service.mapper.SystemUserTaskMapper;
import com.jiyi.modules.user.service.mapper.UserBillMapper;
import com.jiyi.modules.user.service.mapper.YxUserTaskFinishMapper;
import com.jiyi.modules.user.vo.YxSystemUserTaskQueryVo;
import com.jiyi.utils.FileUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
*
* @date 2020-05-12
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class YxSystemUserTaskServiceImpl extends BaseServiceImpl<SystemUserTaskMapper, YxSystemUserTask> implements YxSystemUserTaskService {

    @Autowired
    private IGenerator generator;
    @Autowired
    private YxSystemUserLevelService systemUserLevelService;

    @Autowired
    private SystemUserTaskMapper yxSystemUserTaskMapper;
    @Autowired
    private YxUserTaskFinishMapper yxUserTaskFinishMapper;

    @Autowired
    private UserBillMapper userBillMapper;
    @Autowired
    private StoreOrderMapper storeOrderMapper;

    @Autowired
    private YxUserTaskFinishService userTaskFinishService;
    @Autowired
    private YxUserBillService userBillService;





    /**
     * 获取已经完成的任务数量
     * @param levelId 等级id
     * @param uid uid
     * @return int
     */
    @Override
    public int getTaskComplete(int levelId, Long uid) {
        List<YxSystemUserTask> list = this.lambdaQuery()
                .eq(YxSystemUserTask::getLevelId,levelId)
                .eq(YxSystemUserTask::getIsShow,ShopCommonEnum.SHOW_1.getValue())
                .list();
        List<Integer> taskIds = list.stream().map(YxSystemUserTask::getId)
                .collect(Collectors.toList());
        if(taskIds.isEmpty()) {
            return 0;
        }

        int count = yxUserTaskFinishMapper.selectCount(Wrappers.<YxUserTaskFinish>lambdaQuery()
                .in(YxUserTaskFinish::getTaskId,taskIds)
                .eq(YxUserTaskFinish::getUid,uid));
        return count;
    }

    /**
     * 获取等级会员任务列表
     * @param levelId 等级id
     * @param uid uid
     * @return TaskDto
     */
    @Override
    public TaskDto getTaskList(int levelId, Long uid) {
       LambdaQueryWrapper<YxSystemUserTask> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(YxSystemUserTask::getLevelId,levelId)
                .eq(YxSystemUserTask::getIsShow, ShopCommonEnum.SHOW_1.getValue())
                .orderByDesc(YxSystemUserTask::getSort);
        List<YxSystemUserTaskQueryVo> list = generator.convert(yxSystemUserTaskMapper
                .selectList(wrapper),YxSystemUserTaskQueryVo.class);

        TaskDto taskDTO = new TaskDto();
        taskDTO.setList(list);
        taskDTO.setReachCount(this.getTaskComplete(levelId,uid));
        taskDTO.setTask(this.tidyTask(list,uid));

        return taskDTO;
    }


    /**
     * 设置任务内容完成情况
     * @param task 任务列表
     * @param uid uid
     * @return list
     */
    private List<YxSystemUserTaskQueryVo> tidyTask(List<YxSystemUserTaskQueryVo> task,Long uid) {
        for (YxSystemUserTaskQueryVo taskQueryVo : task) {
            int count = userTaskFinishService.lambdaQuery()
                    .eq(YxUserTaskFinish::getTaskId,taskQueryVo.getId())
                    .eq(YxUserTaskFinish::getUid,uid)
                    .count();
            if(count > 0){
                taskQueryVo.setNewNumber(taskQueryVo.getNumber());
                taskQueryVo.setSpeed(100); //完成比例
                taskQueryVo.setFinish(ShopCommonEnum.IS_FINISH_1.getValue());
                taskQueryVo.setTaskTypeTitle("");
            }else{
                double sumNumber = 0d;
                String title = "";
                switch (taskQueryVo.getTaskType()){
                    case "SatisfactionIntegral":
                        sumNumber = userBillMapper.sumIntegral(uid);
                        title = "还需要{0}经验";
                        break;
                    case "ConsumptionAmount":
                        sumNumber = storeOrderMapper.sumPrice(uid);
                        title = "还需消费{0}元";
                        break;
                    case "CumulativeAttendance":
                        sumNumber = userBillService.cumulativeAttendance(uid);
                        title = "还需签到{0}天";
                        break;
                }

                if(sumNumber >= taskQueryVo.getNumber()){
                    userTaskFinishService.setFinish(uid,taskQueryVo.getId());
                    taskQueryVo.setFinish(ShopCommonEnum.IS_FINISH_1.getValue());
                    taskQueryVo.setSpeed(100);
                    taskQueryVo.setTaskTypeTitle("");
                    taskQueryVo.setNewNumber(taskQueryVo.getNumber());
                }else{
                    double numdata = NumberUtil.sub(taskQueryVo.getNumber().doubleValue(),sumNumber);
                    taskQueryVo.setTaskTypeTitle(MessageFormat.format(title,numdata));
                    double speed = NumberUtil.div(sumNumber,taskQueryVo.getNumber().doubleValue());
                    taskQueryVo.setSpeed(Double.valueOf(NumberUtil.mul(speed,100)).intValue());
                    taskQueryVo.setFinish(ShopCommonEnum.IS_FINISH_0.getValue());
                    taskQueryVo.setNewNumber(Double.valueOf(sumNumber).intValue());
                }
            }
        }

        return task;
    }


    //==========================================================//

    @Override
    //@Cacheable
    public Map<String, Object> queryAll(YxSystemUserTaskQueryCriteria criteria, Pageable pageable) {
        getPage(pageable);
        PageInfo<YxSystemUserTask> page = new PageInfo<>(queryAll(criteria));
        List<YxSystemUserTaskDto> systemUserTaskDTOS = generator.convert(page.getList(),YxSystemUserTaskDto.class);
        for (YxSystemUserTaskDto systemUserTaskDTO : systemUserTaskDTOS) {
            YxSystemUserLevel userLevel=systemUserLevelService.getById(systemUserTaskDTO.getLevelId());
            if(userLevel == null) {
                continue;
            }
            systemUserTaskDTO.setLevalName(userLevel.getName());
        }
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", systemUserTaskDTOS);
        map.put("totalElements", page.getTotal());
        return map;
    }


    @Override
    //@Cacheable
    public List<YxSystemUserTask> queryAll(YxSystemUserTaskQueryCriteria criteria){
        return baseMapper.selectList(QueryHelpPlus.getPredicate(YxSystemUserTask.class, criteria));
    }


    @Override
    public void download(List<YxSystemUserTaskDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (YxSystemUserTaskDto yxSystemUserTask : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("任务名称", yxSystemUserTask.getName());
            map.put("配置原名", yxSystemUserTask.getRealName());
            map.put("任务类型", yxSystemUserTask.getTaskType());
            map.put("限定数", yxSystemUserTask.getNumber());
            map.put("等级id", yxSystemUserTask.getLevelId());
            map.put("排序", yxSystemUserTask.getSort());
            map.put("是否显示", yxSystemUserTask.getIsShow());
            map.put("是否务必达成任务,1务必达成,0=满足其一", yxSystemUserTask.getIsMust());
            map.put("任务说明", yxSystemUserTask.getIllustrate());
            map.put("新增时间", yxSystemUserTask.getAddTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
