
package com.jiyi.modules.mp.service;

import com.jiyi.common.service.BaseService;
import com.jiyi.modules.mp.service.dto.YxWechatLiveGoodsDto;
import com.jiyi.modules.mp.service.dto.YxWechatLiveGoodsQueryCriteria;
import com.jiyi.modules.mp.domain.YxWechatLiveGoods;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
*
* @date 2020-08-11
*/
public interface YxWechatLiveGoodsService  extends BaseService<YxWechatLiveGoods>{

/**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(YxWechatLiveGoodsQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<YxWechatLiveGoodsDto>
    */
    List<YxWechatLiveGoods> queryAll(YxWechatLiveGoodsQueryCriteria criteria);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<YxWechatLiveGoodsDto> all, HttpServletResponse response) throws IOException;

    /**
     * 保存直播商品信息
     * @param resources
     * @return
     */
    boolean saveGoods(YxWechatLiveGoods resources);

    /**
     * 同步商品更新审核状态
     * @param goodsIds
     * @return
     */
    boolean synchroWxOlLive(List<Integer> goodsIds);

    /**
     * 根据id删除直播商品信息
     * @param id
     */
    void removeGoods(Long id);

    /**
     * 更新直播商品信息
     * @param resources
     */
    void updateGoods(YxWechatLiveGoods resources);
}
