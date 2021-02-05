
package com.jiyi.modules.shop.service;

import com.jiyi.common.service.BaseService;
import com.jiyi.modules.shop.domain.YxSystemGroupData;
import com.jiyi.modules.shop.service.dto.YxSystemGroupDataDto;
import com.jiyi.modules.shop.service.dto.YxSystemGroupDataQueryCriteria;
import com.alibaba.fastjson.JSONObject;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
*
* @date 2020-05-12
*/
public interface YxSystemGroupDataService  extends BaseService<YxSystemGroupData>{

    /**
     * 获取配置数据
     * @param name 配置名称
     * @return List
     */
    List<JSONObject> getDatas(String name);

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(YxSystemGroupDataQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<YxSystemGroupDataDto>
    */
    List<YxSystemGroupData> queryAll(YxSystemGroupDataQueryCriteria criteria);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<YxSystemGroupDataDto> all, HttpServletResponse response) throws IOException;
}
