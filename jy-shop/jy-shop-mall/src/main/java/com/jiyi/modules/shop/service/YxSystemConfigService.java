
package com.jiyi.modules.shop.service;

import com.jiyi.common.service.BaseService;
import com.jiyi.modules.shop.domain.YxSystemConfig;
import com.jiyi.modules.shop.service.dto.YxSystemConfigDto;
import com.jiyi.modules.shop.service.dto.YxSystemConfigQueryCriteria;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
*
* @date 2020-05-12
*/
public interface YxSystemConfigService  extends BaseService<YxSystemConfig>{

    /**
     * 获取配置值
     * @param name 配置名
     * @return string
     */
    String getData(String name);

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(YxSystemConfigQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<YxSystemConfigDto>
    */
    List<YxSystemConfig> queryAll(YxSystemConfigQueryCriteria criteria);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<YxSystemConfigDto> all, HttpServletResponse response) throws IOException;

    YxSystemConfig findByKey(String store_brokerage_statu);
}
