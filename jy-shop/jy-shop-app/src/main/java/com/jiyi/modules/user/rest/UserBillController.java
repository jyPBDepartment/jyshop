
package com.jiyi.modules.user.rest;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.jiyi.api.ApiResult;
import com.jiyi.api.YshopException;
import com.jiyi.logging.aop.log.AppLog;
import com.jiyi.common.bean.LocalUser;
import com.jiyi.common.interceptor.AuthCheck;
import com.jiyi.constant.SystemConfigConstants;
import com.jiyi.enums.BillDetailEnum;
import com.jiyi.modules.activity.service.YxUserExtractService;
import com.jiyi.modules.services.CreatShareProductService;
import com.jiyi.modules.shop.service.YxSystemConfigService;
import com.jiyi.modules.user.domain.YxUser;
import com.jiyi.modules.user.param.PromParam;
import com.jiyi.modules.user.param.YxUserBillQueryParam;
import com.jiyi.modules.user.service.YxUserBillService;
import com.jiyi.modules.user.service.YxUserService;
import com.jiyi.modules.user.vo.YxUserBillQueryVo;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName UserBillController
 *
 * @Date 2019/11/10
 **/
@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Api(value = "用户分销", tags = "用户:用户分销", description = "用户分销")
public class UserBillController {

    private final YxUserBillService userBillService;
    private final YxUserExtractService extractService;
    private final YxSystemConfigService systemConfigService;
    private final YxUserService yxUserService;
    private final CreatShareProductService creatShareProductService;

    @Value("${file.path}")
    private String path;

    /**
     * 推广数据    昨天的佣金   累计提现金额  当前佣金
     */
    @AppLog(value = "查看推广数据", type = 1)
    @AuthCheck
    @GetMapping("/commission")
    @ApiOperation(value = "推广数据",notes = "推广数据")
    public ApiResult<Map<String,Object>> commission(){
        YxUser yxUser = LocalUser.getUser();

        //昨天的佣金
        double lastDayCount = userBillService.yesterdayCommissionSum(yxUser.getUid());
        //累计提现金额
        double extractCount = extractService.extractSum(yxUser.getUid());

        Map<String,Object> map = Maps.newHashMap();
        map.put("lastDayCount",lastDayCount);
        map.put("extractCount",extractCount);
        map.put("commissionCount",yxUser.getBrokeragePrice());

        return ApiResult.ok(map);
    }

    /**
     * 积分记录
     */
    @AppLog(value = "查看积分记录", type = 1)
    @AuthCheck
    @GetMapping("/integral/list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码,默认为1", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "limit", value = "页大小,默认为10", paramType = "query", dataType = "int")
    })
    @ApiOperation(value = "积分记录",notes = "积分记录")
    public ApiResult<List<YxUserBillQueryVo>> userInfo(@RequestParam(value = "page",defaultValue = "1") int page,
                                                       @RequestParam(value = "limit",defaultValue = "10") int limit){
        Long uid = LocalUser.getUser().getUid();
        return ApiResult.ok(userBillService.userBillList(uid, BillDetailEnum.CATEGORY_2.getValue()
                ,page, limit));
    }


    /**
     * 分销二维码海报生成
     */
    @AppLog(value = "分销二维码海报生成", type = 1)
    @AuthCheck
    @GetMapping("/spread/banner")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "form", value = "来源", paramType = "query", dataType = "string")
    })
    @ApiOperation(value = "分销二维码海报生成",notes = "分销二维码海报生成")
    public ApiResult<List<Map<String,Object>>> spreadBanner(@RequestParam(value = "",required=false) String from){
        YxUser yxUser = LocalUser.getUser();
        String siteUrl = systemConfigService.getData(SystemConfigConstants.SITE_URL);
        if(StrUtil.isEmpty(siteUrl)){
            throw new YshopException("未配置h5地址!");
        }
        String apiUrl = systemConfigService.getData(SystemConfigConstants.API_URL);
        if(StrUtil.isEmpty(apiUrl)){
            throw new YshopException("未配置api地址!");
        }

        String spreadUrl = creatShareProductService.getSpreadUrl(from,yxUser,siteUrl,apiUrl,path);

        List<Map<String,Object>> list = new ArrayList<>();

        Map<String,Object> map = Maps.newHashMap();
        map.put("id",1);
        map.put("pic","");
        map.put("title","分享海报");
        map.put("wap_poster",spreadUrl);
        list.add(map);
        return ApiResult.ok(list);
    }


    /**
     *  推广人统计
     */
    @AppLog(value = "查看推广人统计", type = 1)
    @AuthCheck
    @PostMapping("/spread/people")
    @ApiOperation(value = "推广人统计",notes = "推广人统计")
    public ApiResult<Map<String,Object>> spreadPeople(@Valid @RequestBody PromParam param){
        Long uid = LocalUser.getUser().getUid();
        Map<String,Object> map = new LinkedHashMap<>();
        map.put("list",yxUserService.getUserSpreadGrade(uid,param.getPage(),param.getLimit()
                ,param.getGrade(),param.getKeyword(),param.getSort()));

        Map<String,Integer> countMap = yxUserService.getSpreadCount(uid);
        map.put("total",countMap.get("first"));
        map.put("totalLevel",countMap.get("second"));
        return ApiResult.ok(map);
    }

    /**
     * 推广佣金明细
     * type  0 全部  1 消费  2 充值  3 返佣  4 提现
     * @return mixed
     */
    @AppLog(value = "查看推广佣金明细", type = 1)
    @AuthCheck
    @GetMapping("/spread/commission/{type}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码,默认为1", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "limit", value = "页大小,默认为10", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "type", value = "类型 0所有 1消费 2充值 3返佣 4提现 5签到积分 6退款 7系统增加 8系统减少", paramType = "query", dataType = "int")
    })
    @ApiOperation(value = "推广佣金明细",notes = "推广佣金明细")
    public ApiResult<Object> spreadCommission(@RequestParam(value = "page",defaultValue = "1") int page,
                                              @RequestParam(value = "limit",defaultValue = "10") int limit,
                                              @PathVariable String type){
        int newType = 0;
        if(NumberUtil.isNumber(type)) {
            newType = Integer.valueOf(type);
        }
        Long uid = LocalUser.getUser().getUid();
        return ApiResult.ok(userBillService.getUserBillList(page,limit,uid,newType));
    }


    /**
     * 推广订单
     */
    @AppLog(value = "查看推广订单", type = 1)
    @AuthCheck
    @PostMapping("/spread/order")
    @ApiOperation(value = "推广订单",notes = "推广订单")
    public ApiResult<Object> spreadOrder(@RequestBody YxUserBillQueryParam param){
        Long uid = LocalUser.getUser().getUid();
        Map<String, Object> map = userBillService.spreadOrder(uid,param.getPage(),param.getLimit());
        return ApiResult.ok(map);
    }



}
