
package com.jiyi.modules.mp.service;

import com.jiyi.common.service.BaseService;
import com.jiyi.modules.mp.service.dto.YxArticleDto;
import com.jiyi.modules.mp.service.dto.YxArticleQueryCriteria;
import com.jiyi.modules.mp.domain.YxArticle;
import com.jiyi.modules.mp.vo.YxArticleQueryVo;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
*
* @date 2020-05-12
*/
public interface YxArticleService  extends BaseService<YxArticle>{

    /**
     * 获取文章列表
     * @param page 页码
     * @param limit 条数
     * @return List
     */
    List<YxArticleQueryVo> getList(int page, int limit);

    /**
     * 获取文章详情
     * @param id id
     * @return YxArticleQueryVo
     */
    YxArticleQueryVo getDetail(int id);

    void incVisitNum(int id);

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(YxArticleQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<YxArticleDto>
    */
    List<YxArticle> queryAll(YxArticleQueryCriteria criteria);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<YxArticleDto> all, HttpServletResponse response) throws IOException;


}
