
package com.jiyi.modules.product.service;


import com.jiyi.common.service.BaseService;
import com.jiyi.domain.PageResult;
import com.jiyi.modules.product.domain.YxStoreProductRelation;
import com.jiyi.modules.product.service.dto.YxStoreProductRelationDto;
import com.jiyi.modules.product.service.dto.YxStoreProductRelationQueryCriteria;
import com.jiyi.modules.product.vo.YxStoreProductRelationQueryVo;
import javax.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Pageable;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 商品点赞和收藏表 服务类
 * </p>
 *
 *
 * @since 2019-10-23
 */
public interface YxStoreProductRelationService extends BaseService<YxStoreProductRelation> {

    /**
     * 是否收藏
     * @param productId 商品ID
     * @param uid 用户ID
     * @return Boolean
     */
    Boolean isProductRelation(long productId, long uid);

    /**
     *添加收藏
     * @param productId 商品id
     * @param uid 用户id
     */
    void addRroductRelation(long productId,long uid,String category);

    /**
     * 取消收藏
     * @param productId 商品id
     * @param uid 用户id
     */
    void delRroductRelation(long productId,long uid,String category);

    /**
     * 获取用户收藏列表
     * @param page page
     * @param limit limit
     * @param uid 用户id
     * @return list
     */
    List<YxStoreProductRelationQueryVo> userCollectProduct(int page, int limit, Long uid,String type);

    /**
     * 查询数据分页
     * @param criteria 条件
     * @param pageable 分页参数
     * @return Map<String,Object>
     */
    PageResult<YxStoreProductRelationDto> queryAll(YxStoreProductRelationQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     * @param criteria 条件参数
     * @return List<YxStoreProductRelationDto>
     */
    List<YxStoreProductRelation> queryAll(YxStoreProductRelationQueryCriteria criteria);

    /**
     * 导出数据
     * @param all 待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<YxStoreProductRelationDto> all, HttpServletResponse response) throws IOException;


}
