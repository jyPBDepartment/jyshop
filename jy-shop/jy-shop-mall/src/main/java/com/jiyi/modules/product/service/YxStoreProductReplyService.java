
package com.jiyi.modules.product.service;

import com.jiyi.common.service.BaseService;
import com.jiyi.modules.product.domain.YxStoreProductReply;
import com.jiyi.modules.product.service.dto.YxStoreProductReplyDto;
import com.jiyi.modules.product.service.dto.YxStoreProductReplyQueryCriteria;
import com.jiyi.modules.product.vo.ReplyCountVo;
import com.jiyi.modules.product.vo.YxStoreProductReplyQueryVo;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
*
* @date 2020-05-12
*/
public interface YxStoreProductReplyService  extends BaseService<YxStoreProductReply>{

    /**
     * 评价数据
     * @param productId 商品id
     * @return ReplyCountVO
     */
    ReplyCountVo getReplyCount(long productId);

    /**
     * 处理评价
     * @param replyQueryVo replyQueryVo
     * @return YxStoreProductReplyQueryVo
     */
    YxStoreProductReplyQueryVo handleReply(YxStoreProductReplyQueryVo replyQueryVo);

    /**
     * 获取单条评价
     * @param productId 商品di
     * @return YxStoreProductReplyQueryVo
     */
    YxStoreProductReplyQueryVo getReply(long productId);

    /**
     * 获取评价列表
     * @param productId 商品id
     * @param type 0-全部 1-好评 2-中评 3-差评
     * @param page page
     * @param limit limit
     * @return list
     */
    List<YxStoreProductReplyQueryVo> getReplyList(long productId,int type,int page, int limit);

    int getInfoCount(Integer oid, String unique);

    int productReplyCount(long productId);

    int replyCount(String unique);

    String replyPer(long productId);


    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(YxStoreProductReplyQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<YxStoreProductReplyDto>
    */
    List<YxStoreProductReply> queryAll(YxStoreProductReplyQueryCriteria criteria);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<YxStoreProductReplyDto> all, HttpServletResponse response) throws IOException;
}
