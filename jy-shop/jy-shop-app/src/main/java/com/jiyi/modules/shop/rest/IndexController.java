
package com.jiyi.modules.shop.rest;

import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.resource.ClassPathResource;
import com.jiyi.api.ApiResult;
import com.jiyi.api.YshopException;
import com.jiyi.constant.ShopConstants;
import com.jiyi.enums.ProductEnum;
import com.jiyi.modules.activity.service.YxStoreCombinationService;
import com.jiyi.modules.activity.service.YxStoreSeckillService;
import com.jiyi.modules.activity.vo.YxStoreSeckillQueryVo;
import com.jiyi.modules.mp.service.YxWechatLiveService;
import com.jiyi.modules.product.service.YxStoreProductService;
import com.jiyi.modules.product.vo.YxSystemStoreQueryVo;
import com.jiyi.modules.shop.param.YxSystemStoreQueryParam;
import com.jiyi.modules.shop.service.YxSystemGroupDataService;
import com.jiyi.modules.shop.service.YxSystemStoreService;
import com.jiyi.modules.shop.vo.IndexVo;
import com.jiyi.utils.FileUtil;
import com.jiyi.utils.RedisUtil;
import com.jiyi.utils.ShopKeyUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName IndexController
 *
 * @Date 2019/10/19
 **/
@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Api(value = "首页模块", tags = "商城:首页模块", description = "首页模块")
public class IndexController {

    private final YxSystemGroupDataService systemGroupDataService;
    private final YxStoreProductService storeProductService;
    private final YxSystemStoreService systemStoreService;
    private final YxStoreCombinationService storeCombinationService;
    private final YxStoreSeckillService storeSeckillService;
    private final YxWechatLiveService wechatLiveService;

    @Cacheable(cacheNames = ShopConstants.YSHOP_REDIS_INDEX_KEY)
    @GetMapping("/index")
    @ApiOperation(value = "首页数据",notes = "首页数据")
    public ApiResult<IndexVo> index(){
        IndexVo indexVo = IndexVo.builder()
                .banner(systemGroupDataService.getDatas(ShopConstants.YSHOP_HOME_BANNER))
                .bastList(storeProductService.getList(1,6, ProductEnum.TYPE_1.getValue()))
                .benefit(storeProductService.getList(1,10,ProductEnum.TYPE_4.getValue()))
                .combinationList(storeCombinationService.getList(1,8).getStoreCombinationQueryVos())
                .firstList(storeProductService.getList(1,6,ProductEnum.TYPE_3.getValue()))
                .likeInfo(storeProductService.getList(1,8,ProductEnum.TYPE_2.getValue()))
                .mapKey(RedisUtil.get(ShopKeyUtils.getTengXunMapKey()))
                .menus(systemGroupDataService.getDatas(ShopConstants.YSHOP_HOME_MENUS))
                .roll(systemGroupDataService.getDatas(ShopConstants.YSHOP_HOME_ROLL_NEWS))
                .seckillList(storeSeckillService.getList(1, 4))
                .liveList(wechatLiveService.getList(1,4,0))
                .build();
        return ApiResult.ok(indexVo);
    }

    @GetMapping("/search/keyword")
    @ApiOperation(value = "热门搜索关键字获取",notes = "热门搜索关键字获取")
    public ApiResult<List<String>> search(){
        List<JSONObject> list = systemGroupDataService.getDatas(ShopConstants.YSHOP_HOT_SEARCH);
        List<String>  stringList = new ArrayList<>();
        for (JSONObject object : list) {
            stringList.add(object.getString("title"));
        }
        return ApiResult.ok(stringList);
    }


    @PostMapping("/image_base64")
    @ApiOperation(value = "获取图片base64",notes = "获取图片base64")
    @Deprecated
    public ApiResult<List<String>> imageBase64(){
        return ApiResult.ok(null);
    }


    @GetMapping("/citys")
    @ApiOperation(value = "获取城市json",notes = "获取城市json")
    public ApiResult<JSONObject> cityJson(){
        String path = "city.json";
        String name = "city.json";
        try {
            File file = FileUtil.inputStreamToFile(new ClassPathResource(path).getStream(), name);
            FileReader fileReader = new FileReader(file,"UTF-8");
            String string = fileReader.readString();
            System.out.println(string);
            JSONObject jsonObject = JSON.parseObject(string);
            return ApiResult.ok(jsonObject);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new YshopException("无数据");
        }

    }


    @GetMapping("/store_list")
    @ApiOperation(value = "获取门店列表",notes = "获取门店列表")
    public ApiResult<Map<String,Object>> storeList( YxSystemStoreQueryParam param){
        Map<String,Object> map = new LinkedHashMap<>();
        List<YxSystemStoreQueryVo> lists = systemStoreService.getStoreList(
                param.getLatitude(),
                param.getLongitude(),
                param.getPage(),param.getLimit());
        map.put("list",lists);
        return ApiResult.ok(map);
    }




}
