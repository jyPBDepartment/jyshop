
package com.jiyi.modules.order.service;

import com.jiyi.common.service.BaseService;
import com.jiyi.modules.order.domain.YxStoreOrderStatus;
import com.jiyi.modules.order.service.dto.YxStoreOrderStatusDto;
import com.jiyi.modules.order.service.dto.YxStoreOrderStatusQueryCriteria;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
*
* @date 2020-05-12
*/
public interface YxStoreOrderStatusService  extends BaseService<YxStoreOrderStatus>{

    /**
     * 添加订单操作记录
     * @param oid 订单id
     * @param changetype 操作状态
     * @param changeMessage 操作内容
     */
    void create(Long oid,String changetype,String changeMessage);

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(YxStoreOrderStatusQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<YxStoreOrderStatusDto>
    */
    List<YxStoreOrderStatus> queryAll(YxStoreOrderStatusQueryCriteria criteria);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<YxStoreOrderStatusDto> all, HttpServletResponse response) throws IOException;
}
