
package com.jiyi.modules.user.rest;


import com.jiyi.api.ApiResult;
import com.jiyi.logging.aop.log.AppLog;
import com.jiyi.common.bean.LocalUser;
import com.jiyi.common.interceptor.AuthCheck;
import com.jiyi.constant.SystemConfigConstants;
import com.jiyi.modules.activity.param.UserExtParam;
import com.jiyi.modules.activity.service.YxUserExtractService;
import com.jiyi.modules.shop.service.YxSystemConfigService;
import com.jiyi.modules.user.domain.YxUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>
 * 用户提现 前端控制器
 * </p>
 *
 *
 * @since 2019-11-11
 */
@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Api(value = "用户提现", tags = "用户:用户提现", description = "用户提现")
public class UserExtractController {

    private final YxUserExtractService userExtractService;
    private final YxSystemConfigService systemConfigService;

    /**
     * 提现参数
     */
    @AuthCheck
    @GetMapping("/extract/bank")
    @ApiOperation(value = "提现参数",notes = "提现参数")
    public ApiResult<Object> bank(){
        YxUser yxUser = LocalUser.getUser();
        Map<String,Object> map = new LinkedHashMap<>();
        map.put("commissionCount",yxUser.getBrokeragePrice());
        map.put("minPrice",systemConfigService.getData(SystemConfigConstants.USER_EXTRACT_MIN_PRICE));
        return ApiResult.ok(map);
    }


    /**
    * 用户提现
    */
    @AppLog(value = "用户提现", type = 1)
    @AuthCheck
    @PostMapping("/extract/cash")
    @ApiOperation(value = "用户提现",notes = "用户提现")
    public ApiResult<String> addYxUserExtract(@Valid @RequestBody UserExtParam param){
        YxUser yxUser = LocalUser.getUser();
        userExtractService.userExtract(yxUser,param);
        return ApiResult.ok("申请提现成功");
    }




}

