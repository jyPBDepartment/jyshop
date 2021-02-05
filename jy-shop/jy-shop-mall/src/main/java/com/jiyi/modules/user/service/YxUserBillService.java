
package com.jiyi.modules.user.service;

import com.jiyi.common.service.BaseService;
import com.jiyi.modules.user.domain.YxUserBill;
import com.jiyi.modules.user.service.dto.YxUserBillDto;
import com.jiyi.modules.user.service.dto.YxUserBillQueryCriteria;
import com.jiyi.modules.user.vo.BillVo;
import com.jiyi.modules.user.vo.YxUserBillQueryVo;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
*
* @date 2020-05-12
*/
public interface YxUserBillService  extends BaseService<YxUserBill>{

    /**
     * 增加支出流水
     * @param uid uid
     * @param title 账单标题
     * @param category 明细种类
     * @param type 明细类型
     * @param number 明细数字
     * @param balance 剩余
     * @param mark 备注
     */
    void expend(Long uid,String title,String category,String type,double number,double balance,String mark);

    /**
     * 增加收入/支入流水
     * @param uid uid
     * @param title 账单标题
     * @param category 明细种类
     * @param type 明细类型
     * @param number 明细数字
     * @param balance 剩余
     * @param mark 备注
     * @param linkid 关联id
     */
    void income(Long uid,String title,String category,String type,double number,
                double balance,String mark,String linkid);

    int cumulativeAttendance(Long uid);

    /**
     * 获取推广订单列表
     * @param uid   uid
     * @param page  page
     * @param limit limit
     * @return Map
     */
    Map<String,Object> spreadOrder(Long uid,int page,int limit);

    /**
     * 获取用户账单记录
     * @param page page
     * @param limit limit
     * @param uid uid
     * @param type BillDetailEnum
     * @return list
     */
    List<BillVo> getUserBillList(int page, int limit, long uid, int type);

    double getBrokerage(int uid);

    /**
     * 统计昨天的佣金
     * @param uid uid
     * @return double
     */
    double yesterdayCommissionSum(Long uid);

    /**
     * 根据类别获取账单记录
     * @param uid uid
     * @param category  BillDetailEnum
     * @param page page
     * @param limit limit
     * @return List
     */
    List<YxUserBillQueryVo> userBillList(Long uid,String category,int page,int limit);

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(YxUserBillQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<YxUserBillDto>
    */
    List<YxUserBillDto> queryAll(YxUserBillQueryCriteria criteria);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<YxUserBillDto> all, HttpServletResponse response) throws IOException;
}
