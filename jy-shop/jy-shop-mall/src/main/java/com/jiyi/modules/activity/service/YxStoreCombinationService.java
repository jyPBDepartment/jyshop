
package com.jiyi.modules.activity.service;

import com.jiyi.common.service.BaseService;
import com.jiyi.modules.activity.domain.YxStoreCombination;
import com.jiyi.modules.activity.service.dto.YxStoreCombinationDto;
import com.jiyi.modules.activity.service.dto.YxStoreCombinationQueryCriteria;
import com.jiyi.modules.activity.vo.CombinationQueryVo;
import com.jiyi.modules.activity.vo.StoreCombinationVo;
import com.jiyi.modules.activity.vo.YxStoreCombinationQueryVo;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
*
* @date 2020-05-13
*/
public interface YxStoreCombinationService  extends BaseService<YxStoreCombination>{



    /**
     * 拼团列表
     * @param page page
     * @param limit limit
     * @return list
     */
    CombinationQueryVo getList(int page, int limit);

    /**
     * 获取拼团详情
     * @param id 拼团产品id
     * @param uid uid
     * @return StoreCombinationVo
     */
    StoreCombinationVo getDetail(Long id, Long uid);

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(YxStoreCombinationQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<YxStoreCombinationDto>
    */
    List<YxStoreCombination> queryAll(YxStoreCombinationQueryCriteria criteria);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<YxStoreCombinationDto> all, HttpServletResponse response) throws IOException;

    /**
     * 修改状态
     * @param id 拼团产品id
     * @param status ShopCommonEnum
     */
    void onSale(Long id, Integer status);

    boolean saveCombination(YxStoreCombinationDto resources);
}
