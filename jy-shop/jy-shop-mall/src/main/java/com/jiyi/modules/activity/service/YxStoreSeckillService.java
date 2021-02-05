
package com.jiyi.modules.activity.service;

import com.jiyi.common.service.BaseService;
import com.jiyi.modules.activity.domain.YxStoreSeckill;
import com.jiyi.modules.activity.service.dto.YxStoreSeckillDto;
import com.jiyi.modules.activity.service.dto.YxStoreSeckillQueryCriteria;
import com.jiyi.modules.activity.vo.StoreSeckillVo;
import com.jiyi.modules.activity.vo.YxStoreSeckillQueryVo;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
*
* @date 2020-05-13
*/
public interface YxStoreSeckillService  extends BaseService<YxStoreSeckill>{


    /**
     * 产品详情
     * @param id 秒杀商品id
     * @return StoreSeckillVo
     */
    StoreSeckillVo getDetail(Long id);

    /**
     * 秒杀产品列表
     * @param page page
     * @param limit limit
     * @return list
     */
    List<YxStoreSeckillQueryVo> getList(int page, int limit, int time);

    /**
     * 秒杀产品列表(首页用)
     * @param page page
     * @param limit limit
     * @return list
     */
    List<YxStoreSeckillQueryVo> getList(int page, int limit);

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(YxStoreSeckillQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<YxStoreSeckillDto>
    */
    List<YxStoreSeckill> queryAll(YxStoreSeckillQueryCriteria criteria);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<YxStoreSeckillDto> all, HttpServletResponse response) throws IOException;

    boolean saveSeckill(YxStoreSeckillDto resources);
}
