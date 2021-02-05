
package com.jiyi.modules.activity.service;

import com.jiyi.common.service.BaseService;
import com.jiyi.modules.activity.domain.YxStoreBargain;
import com.jiyi.modules.activity.service.dto.YxStoreBargainDto;
import com.jiyi.modules.activity.service.dto.YxStoreBargainQueryCriteria;
import com.jiyi.modules.activity.vo.BargainCountVo;
import com.jiyi.modules.activity.vo.BargainVo;
import com.jiyi.modules.activity.vo.TopCountVo;
import com.jiyi.modules.activity.vo.YxStoreBargainQueryVo;
import com.jiyi.modules.user.domain.YxUser;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
*
* @date 2020-05-13
*/
public interface YxStoreBargainService  extends BaseService<YxStoreBargain>{

    /**
     * 退回库存销量
     * @param num 数量
     * @param bargainId 砍价产品id
     */
    void incStockDecSales(int num,Long bargainId);

    /**
     * 增加销量 减少库存
     * @param num 数量
     * @param bargainId 砍价id
     */
    void decStockIncSales(int num,Long bargainId);

    //YxStoreBargain getBargain(int bargainId);

    /**
     * 开始帮助好友砍价
     * @param bargainId 砍价产品id
     * @param bargainUserUid 开启砍价用户id
     * @param uid 当前用户id
     */
    void doHelp(Long bargainId,Long bargainUserUid,Long uid);

    /**
     * 顶部统计
     * @param bargainId 砍价商品id
     * @return TopCountVo
     */
    TopCountVo topCount(Long bargainId);

    /**
     * 砍价 砍价帮总人数、剩余金额、进度条、已经砍掉的价格
     * @param bargainId 砍价商品id
     * @param uid 砍价用户id
     * @param myUid 当前用户id
     * @return BargainCountVo
     */
    BargainCountVo helpCount(Long bargainId, Long uid, Long myUid);

    //int getBargainPayCount(int bargainId);

    //void addBargainShare(int id);

    //void addBargainLook(int id);

    /**
     * 砍价详情
     * @param id 砍价id
     * @param yxUser 用户
     * @return BargainVo
     */
    BargainVo getDetail(Long id, YxUser yxUser);


    /**
     * 获取砍价商品列表
     * @param page page
     * @param limit limit
     * @return List
     */
    List<YxStoreBargainQueryVo> getList(int page, int limit);


    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(YxStoreBargainQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<YxStoreBargainDto>
    */
    List<YxStoreBargain> queryAll(YxStoreBargainQueryCriteria criteria);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<YxStoreBargainDto> all, HttpServletResponse response) throws IOException;

    /**
     * 删除砍价海报
     * @param id
     */
    void deleteBargainImg(String id);
}
