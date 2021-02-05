
package com.jiyi.modules.user.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.jiyi.api.YshopException;
import com.jiyi.common.service.impl.BaseServiceImpl;
import com.jiyi.common.utils.QueryHelpPlus;
import com.jiyi.dozer.service.IGenerator;
import com.jiyi.enums.ShopCommonEnum;
import com.jiyi.modules.shop.domain.YxSystemUserLevel;
import com.jiyi.modules.user.domain.YxUserLevel;
import com.jiyi.modules.user.service.YxSystemUserLevelService;
import com.jiyi.modules.user.service.YxSystemUserTaskService;
import com.jiyi.modules.user.service.YxUserLevelService;
import com.jiyi.modules.user.service.dto.TaskDto;
import com.jiyi.modules.user.service.dto.UserLevelDto;
import com.jiyi.modules.user.service.dto.YxSystemUserLevelDto;
import com.jiyi.modules.user.service.dto.YxSystemUserLevelQueryCriteria;
import com.jiyi.modules.user.service.mapper.SystemUserLevelMapper;
import com.jiyi.modules.user.vo.YxSystemUserLevelQueryVo;
import com.jiyi.utils.FileUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;



/**
*
* @date 2020-05-12
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class YxSystemUserLevelServiceImpl extends BaseServiceImpl<SystemUserLevelMapper, YxSystemUserLevel> implements YxSystemUserLevelService {

    @Autowired
    private IGenerator generator;

    @Autowired
    private SystemUserLevelMapper yxSystemUserLevelMapper;
    @Autowired
    private YxUserLevelService userLevelService;
    @Autowired
    private YxSystemUserTaskService systemUserTaskService;

    /**
     * 获取当前的下一个会员等级id
     * @param levelId 等级id
     * @return int
     */
    @Override
    public int getNextLevelId(int levelId) {
        List<YxSystemUserLevel> list = this.lambdaQuery()
                .eq(YxSystemUserLevel::getIsShow,ShopCommonEnum.SHOW_1.getValue())
                .orderByAsc(YxSystemUserLevel::getGrade)
                .list();

        int grade = 0;
        for (YxSystemUserLevel userLevel : list) {
            if(userLevel.getId() == levelId) {
                grade = userLevel.getGrade();
            }
        }

        YxSystemUserLevel userLevel = this.lambdaQuery()
                .eq(YxSystemUserLevel::getIsShow,ShopCommonEnum.SHOW_1.getValue())
                .orderByAsc(YxSystemUserLevel::getGrade)
                .gt(YxSystemUserLevel::getGrade,grade)
                .last("limit 1")
                .one();
        if(ObjectUtil.isNull(userLevel)) {
            return 0;
        }
        return userLevel.getId();
    }

//    @Override
//    public boolean getClear(int levelId) {
//        List<YxSystemUserLevelQueryVo> systemUserLevelQueryVos = this.getLevelListAndGrade(levelId);
//        for (YxSystemUserLevelQueryVo userLevelQueryVo : systemUserLevelQueryVos) {
//            if(userLevelQueryVo.getId() == levelId) return userLevelQueryVo.getIsClear();
//        }
//        return false;
//    }



    /**
     * 获取会员等级列表及其任务列表
     * @return UserLevelDto
     */
    @Override
    public UserLevelDto getLevelInfo(Long uid) {
        int levelId = 0; //用户当前等级id 0-表示无
        YxUserLevel userLevel = userLevelService.getUserLevel(uid, null);
        if(userLevel != null){
            levelId =  userLevel.getLevelId();
        }


        //会员等级列表
        List<YxSystemUserLevelQueryVo> list = this.getLevelListAndGrade(levelId);
        if(list.isEmpty()) {
            throw new YshopException("请后台设置会员等级");
        }

        //任务列表
        TaskDto taskDTO = systemUserTaskService.getTaskList(list.get(0).getId(),uid);

        UserLevelDto userLevelDTO = new UserLevelDto();
        userLevelDTO.setList(list);
        userLevelDTO.setTask(taskDTO);

        return userLevelDTO;
    }

    /**
     * 获取会员等级列表
     * @param levelId 等级id
     * @return list
     */
    private List<YxSystemUserLevelQueryVo> getLevelListAndGrade(Integer levelId) {
        Integer grade = 0;
        List<YxSystemUserLevel> list = this.lambdaQuery()
                .eq(YxSystemUserLevel::getIsShow, ShopCommonEnum.SHOW_1.getValue())
                .orderByAsc(YxSystemUserLevel::getGrade)
                .list();
        List<YxSystemUserLevelQueryVo> newList = generator.convert(list,YxSystemUserLevelQueryVo.class);
        for (YxSystemUserLevelQueryVo userLevelQueryVo : newList) {
            if(userLevelQueryVo.getId().compareTo(levelId) == 0) {
                grade = userLevelQueryVo.getGrade();
            }
            if(grade.compareTo(userLevelQueryVo.getGrade()) < 0){
                userLevelQueryVo.setIsClear(true); //不解锁
            }else{
                userLevelQueryVo.setIsClear(false);//开启会员解锁
            }
        }
        return newList;
    }


    //=========================================================================================//

    @Override
    //@Cacheable
    public Map<String, Object> queryAll(YxSystemUserLevelQueryCriteria criteria, Pageable pageable) {
        getPage(pageable);
        PageInfo<YxSystemUserLevel> page = new PageInfo<>(queryAll(criteria));
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", generator.convert(page.getList(), YxSystemUserLevelDto.class));
        map.put("totalElements", page.getTotal());
        return map;
    }


    @Override
    //@Cacheable
    public List<YxSystemUserLevel> queryAll(YxSystemUserLevelQueryCriteria criteria){
        return baseMapper.selectList(QueryHelpPlus.getPredicate(YxSystemUserLevel.class, criteria));
    }


    @Override
    public void download(List<YxSystemUserLevelDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (YxSystemUserLevelDto yxSystemUserLevel : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("商户id", yxSystemUserLevel.getMerId());
            map.put("会员名称", yxSystemUserLevel.getName());
            map.put("购买金额", yxSystemUserLevel.getMoney());
            map.put("有效时间", yxSystemUserLevel.getValidDate());
            map.put("是否为永久会员", yxSystemUserLevel.getIsForever());
            map.put("是否购买,1=购买,0=不购买", yxSystemUserLevel.getIsPay());
            map.put("是否显示 1=显示,0=隐藏", yxSystemUserLevel.getIsShow());
            map.put("会员等级", yxSystemUserLevel.getGrade());
            map.put("享受折扣", yxSystemUserLevel.getDiscount());
            map.put("会员卡背景", yxSystemUserLevel.getImage());
            map.put("会员图标", yxSystemUserLevel.getIcon());
            map.put("说明", yxSystemUserLevel.getExplain());
            map.put("添加时间", yxSystemUserLevel.getAddTime());
            map.put("是否删除.1=删除,0=未删除", yxSystemUserLevel.getIsDel());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
