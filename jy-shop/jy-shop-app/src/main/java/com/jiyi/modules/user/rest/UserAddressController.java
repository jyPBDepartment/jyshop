
package com.jiyi.modules.user.rest;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.jiyi.api.ApiResult;
import com.jiyi.api.YshopException;
import com.jiyi.logging.aop.log.AppLog;
import com.jiyi.common.bean.LocalUser;
import com.jiyi.common.interceptor.AuthCheck;
import com.jiyi.common.util.CityTreeUtil;
import com.jiyi.common.web.param.IdParam;
import com.jiyi.constant.ShopConstants;
import com.jiyi.modules.template.domain.YxSystemCity;
import com.jiyi.modules.template.service.YxSystemCityService;
import com.jiyi.modules.user.param.AddressParam;
import com.jiyi.modules.user.service.YxUserAddressService;
import com.jiyi.modules.user.vo.CityVo;
import com.jiyi.modules.user.vo.YxUserAddressQueryVo;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户地前端控制器
 * </p>
 *
 *
 * @since 2019-10-28
 */
@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Api(value = "用户地址", tags = "用户:用户地址", description = "用户地址")
public class UserAddressController {

    private final YxUserAddressService userAddressService;
    private final YxSystemCityService systemCityService;


    @Cacheable(cacheNames = ShopConstants.YSHOP_REDIS_CITY_KEY)
    @GetMapping("/city_list")
    @ApiOperation(value = "城市列表",notes = "城市列表")
    public ApiResult<List<CityVo>> getTest() {
        List<YxSystemCity> yxSystemCities = systemCityService.list();

        List<CityVo> cityVOS = Lists.newArrayList();

        for (YxSystemCity systemCity : yxSystemCities){
            CityVo cityVO = new CityVo();

            cityVO.setV(systemCity.getCityId());
            cityVO.setN(systemCity.getName());
            cityVO.setPid(systemCity.getParentId());

            cityVOS.add(cityVO);
        }


        return ApiResult.ok(CityTreeUtil.list2TreeConverter(cityVOS, 0));

    }

    /**
    * 添加或修改地址
    */
    @AppLog(value = "添加或修改地址", type = 1)
    @AuthCheck
    @PostMapping("/address/edit")
    @ApiOperation(value = "添加或修改地址",notes = "添加或修改地址")
    public ApiResult<Map<String,Object>> addYxUserAddress(@Valid @RequestBody AddressParam param){
        Long uid = LocalUser.getUser().getUid();
        Long id = userAddressService.addAndEdit(uid,param);
        Map<String,Object> map = new LinkedHashMap<>();
        map.put("id",id);
        return ApiResult.ok(map);
    }

    /**
     * 设置默认地址
     */
    @AppLog(value = "设置默认地址", type = 1)
    @AuthCheck
    @PostMapping("/address/default/set")
    @ApiOperation(value = "设置默认地址",notes = "设置默认地址")
    public ApiResult<Boolean> setDefault(@Valid @RequestBody IdParam idParam){
        Long uid = LocalUser.getUser().getUid();
        userAddressService.setDefault(uid,Long.valueOf(idParam.getId()));
        return ApiResult.ok();
    }



    /**
    * 删除用户地址
    */
    @PostMapping("/address/del")
    @ApiOperation(value = "删除用户地址",notes = "删除用户地址")
    public ApiResult<Boolean> deleteYxUserAddress(@Valid @RequestBody IdParam idParam){
        userAddressService.removeById(idParam.getId());
        return ApiResult.ok();
    }


    /**
     * 用户地址列表
     */
    @AuthCheck
    @GetMapping("/address/list")
    @ApiOperation(value = "用户地址列表",notes = "用户地址列表")
    public ApiResult<List<YxUserAddressQueryVo>> getYxUserAddressPageList(@RequestParam(value = "page",defaultValue = "1") int page,
                                                                          @RequestParam(value = "limit",defaultValue = "10") int limit){
        Long uid = LocalUser.getUser().getUid();
        List<YxUserAddressQueryVo> addressQueryVos = userAddressService.getList(uid,page,limit);
        return ApiResult.ok(addressQueryVos);
    }

    /**
     * 地址详情
     */
    @GetMapping("/address/detail/{id}")
    @ApiOperation(value = "地址详情",notes = "地址详情")
    public ApiResult<YxUserAddressQueryVo> addressDetail(@PathVariable String id){
        if(StrUtil.isBlank(id) || !NumberUtil.isNumber(id)){
            throw new YshopException("参数非法");
        }
        return ApiResult.ok(userAddressService.getDetail(Long.valueOf(id)));
    }

}

