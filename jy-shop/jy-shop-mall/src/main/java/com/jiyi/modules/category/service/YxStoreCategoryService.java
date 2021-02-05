
package com.jiyi.modules.category.service;

import com.jiyi.common.service.BaseService;
import com.jiyi.modules.category.domain.YxStoreCategory;
import com.jiyi.modules.category.service.dto.YxStoreCategoryDto;
import com.jiyi.modules.category.service.dto.YxStoreCategoryQueryCriteria;
import com.jiyi.utils.CateDTO;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
*
* @date 2020-05-12
*/
public interface YxStoreCategoryService  extends BaseService<YxStoreCategory>{

    List<CateDTO> getList();

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(YxStoreCategoryQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<YxStoreCategoryDto>
    */
    List<YxStoreCategoryDto> queryAll(YxStoreCategoryQueryCriteria criteria);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<YxStoreCategoryDto> all, HttpServletResponse response) throws IOException;

    /**
     * 构建树形
     * @param categoryDTOS 分类列表
     * @return map
     */
    Map<String,Object> buildTree(List<YxStoreCategoryDto> categoryDTOS);

    /**
     * 检测分类是否操过二级
     * @param pid 父级id
     * @return boolean
     */
    boolean checkCategory(int pid);

    /**
     * 检测商品分类必选选择二级
     * @param id 分类id
     * @return boolean
     */
    boolean checkProductCategory(int id);
}
