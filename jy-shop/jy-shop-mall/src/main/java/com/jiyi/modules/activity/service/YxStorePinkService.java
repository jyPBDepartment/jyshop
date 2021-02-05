
package com.jiyi.modules.activity.service;

import com.jiyi.common.service.BaseService;
import com.jiyi.modules.activity.domain.YxStorePink;
import com.jiyi.modules.activity.service.dto.PinkAllDto;
import com.jiyi.modules.activity.service.dto.YxStorePinkDto;
import com.jiyi.modules.activity.service.dto.YxStorePinkQueryCriteria;
import com.jiyi.modules.activity.vo.PinkInfoVo;
import com.jiyi.modules.order.vo.YxStoreOrderQueryVo;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
*
* @date 2020-05-12
*/
public interface YxStorePinkService  extends BaseService<YxStorePink>{


    /**
     * 取消拼团
     * @param uid 用户id
     * @param cid 团购产品id
     * @param pinkId 拼团id
     */
    void removePink(Long uid, Long cid, Long pinkId);

    /**
     * 计算还差几人拼团
     * @param pink 拼团信息
     * @return int
     */
    int surplusPeople(YxStorePink pink);


    /**
     * 拼团明细
     * @param id 拼团id
     * @param uid 用户id
     */
    PinkInfoVo pinkInfo(Long id, Long uid);


    /**
     * 返回正在拼团的人数
     * @param id 拼团id
     * @return int
     */
    int  pinkIngCount(Long id);

    /**
     * 创建拼团
     * @param order 订单
     */
    void createPink(YxStoreOrderQueryVo order);

    /**
     * 判断用户是否在团内
     * @param id 拼团id
     * @param uid 用户id
     * @return boolean true=在
     */
    boolean getIsPinkUid(Long id,Long uid);

    /**
     * 获取拼团完成的商品总件数
     * @return int
     */
    int getPinkOkSumTotalNum();

    /**
     * 获取拼团成功的用户
     * @param uid uid
     * @return list
     */
    List<String> getPinkOkList(Long uid);



    /**
     * 获取团长拼团数据
     * @param cid 拼团产品id
     * @return PinkAllDto pindAll-参与的拼团的id 集合  list-团长参与的列表
     */
    PinkAllDto getPinkAll(Long cid);

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(YxStorePinkQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<YxStorePinkDto>
    */
    List<YxStorePink> queryAll(YxStorePinkQueryCriteria criteria);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<YxStorePinkDto> all, HttpServletResponse response) throws IOException;
}
