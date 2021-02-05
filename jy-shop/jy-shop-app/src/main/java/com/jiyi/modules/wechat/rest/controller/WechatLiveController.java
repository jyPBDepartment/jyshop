
package com.jiyi.modules.wechat.rest.controller;

import cn.binarywang.wx.miniapp.bean.WxMaLiveResult;
import com.jiyi.api.ApiResult;
import com.jiyi.modules.mp.service.YxWechatLiveService;
import com.jiyi.modules.mp.service.dto.YxWechatLiveQueryCriteria;
import com.jiyi.modules.mp.vo.WechatLiveVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
*
* @date 2020-08-10
*/
@AllArgsConstructor
@Api(tags = "wxlive管理")
@RestController
@RequestMapping
public class WechatLiveController {

    private final YxWechatLiveService yxWechatLiveService;


    @GetMapping("yxWechatLive")
    @ApiOperation("查询所有直播间")
    public ApiResult<WechatLiveVo> getYxWechatLives(YxWechatLiveQueryCriteria criteria, Pageable pageable){
        return ApiResult.ok(yxWechatLiveService.queryAll(criteria,pageable));
    }
    @GetMapping("yxWechatLive/getLiveReplay/{id}")
    @ApiOperation("获取直播回放")
    public ApiResult<List<WxMaLiveResult.LiveReplay>>  getLiveReplay(@PathVariable Integer id){
        return ApiResult.ok(yxWechatLiveService.getLiveReplay(id));
    }
}
