
package com.jiyi.modules.user.rest;


import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.jiyi.api.ApiResult;
import com.jiyi.api.YshopException;
import com.jiyi.common.bean.LocalUser;
import com.jiyi.common.interceptor.AuthCheck;
import com.jiyi.modules.user.service.YxSystemUserLevelService;
import com.jiyi.modules.user.service.YxSystemUserTaskService;
import com.jiyi.modules.user.service.YxUserLevelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户等级 前端控制器
 * </p>
 *
 *
 * @since 2019-12-06
 */
@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Api(value = "用户等级", tags = "用户:用户等级", description = "用户等级")
public class UserLevelController {

    private final YxUserLevelService userLevelService;
    private final YxSystemUserLevelService systemUserLevelService;
    private final YxSystemUserTaskService systemUserTaskService;

    /**
    * 会员等级列表
    */
    @AuthCheck
    @GetMapping("/user/level/grade")
    @ApiOperation(value = "会员等级列表",notes = "会员等级列表")
    public ApiResult<Object> getLevelInfo(){
        Long uid = LocalUser.getUser().getUid();
        return ApiResult.ok(systemUserLevelService.getLevelInfo(uid));
    }

    /**
     * 获取等级任务
     */
    @AuthCheck
    @GetMapping("/user/level/task/{id}")
    @ApiOperation(value = "获取等级任务",notes = "获取等级任务")
    public ApiResult<Object> getTask(@PathVariable String id){
        if(StrUtil.isBlank(id) || !NumberUtil.isNumber(id)){
            throw new YshopException("参数非法");
        }
        Long uid = LocalUser.getUser().getUid();
        return ApiResult.ok(systemUserTaskService.getTaskList(Integer.valueOf(id),uid));
    }

    /**
     * 检测用户是否可以成为会员
     */
    @AuthCheck
    @GetMapping("/user/level/detection")
    @ApiOperation(value = "检测用户是否可以成为会员",notes = "检测用户是否可以成为会员")
    public ApiResult<Object> detection(){
        Long uid = LocalUser.getUser().getUid();
        boolean res = userLevelService.setLevelComplete(uid);
        if(res){
            return ApiResult.ok("升级成功!");
        }else{
            throw new YshopException("还不符合升级条件哦!");
        }

    }



}

