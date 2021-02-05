
package com.jiyi.modules.shop.service;

import com.jiyi.common.service.BaseService;
import com.jiyi.modules.product.vo.YxSystemStoreQueryVo;
import com.jiyi.modules.shop.domain.YxSystemStore;
import com.jiyi.modules.shop.service.dto.YxSystemStoreDto;
import com.jiyi.modules.shop.service.dto.YxSystemStoreQueryCriteria;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
*
* @date 2020-05-12
*/
public interface YxSystemStoreService  extends BaseService<YxSystemStore>{

    YxSystemStoreQueryVo getYxSystemStoreById(int id);

    /**
     * 获取门店列表
     * @param latitude 纬度
     * @param longitude 经度
     * @param page page
     * @param limit limit
     * @return List
     */
    List<YxSystemStoreQueryVo> getStoreList(String latitude, String longitude, int page, int limit);

    /**
     * 获取最新单个门店
     * @param latitude 纬度
     * @param longitude 经度
     * @return YxSystemStoreQueryVo
     */
    YxSystemStoreQueryVo getStoreInfo(String latitude,String longitude);

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(YxSystemStoreQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<YxSystemStoreDto>
    */
    List<YxSystemStore> queryAll(YxSystemStoreQueryCriteria criteria);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<YxSystemStoreDto> all, HttpServletResponse response) throws IOException;
}
