
package com.jiyi.modules.product.service;

import com.jiyi.common.service.BaseService;
import com.jiyi.modules.product.domain.YxStoreProductRule;
import com.jiyi.modules.product.service.dto.YxStoreProductRuleDto;
import com.jiyi.modules.product.service.dto.YxStoreProductRuleQueryCriteria;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
*
* @date 2020-06-28
*/
public interface YxStoreProductRuleService  extends BaseService<YxStoreProductRule> {

/**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(YxStoreProductRuleQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<YxStoreProductRuleDto>
    */
    List<YxStoreProductRule> queryAll(YxStoreProductRuleQueryCriteria criteria);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<YxStoreProductRuleDto> all, HttpServletResponse response) throws IOException;
}
