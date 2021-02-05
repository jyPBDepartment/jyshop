
package com.jiyi.modules.activity.service;

import com.jiyi.common.service.BaseService;
import com.jiyi.modules.activity.domain.YxUserExtract;
import com.jiyi.modules.activity.param.UserExtParam;
import com.jiyi.modules.activity.service.dto.YxUserExtractDto;
import com.jiyi.modules.activity.service.dto.YxUserExtractQueryCriteria;
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
public interface YxUserExtractService  extends BaseService<YxUserExtract>{

    /**
     * 开始提现
     * @param userInfo 用户
     * @param param UserExtParam
     */
    void userExtract(YxUser userInfo, UserExtParam param);

    /**
     * 累计提现金额
     * @param uid uid
     * @return double
     */
    double extractSum(Long uid);


    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(YxUserExtractQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<YxUserExtractDto>
    */
    List<YxUserExtract> queryAll(YxUserExtractQueryCriteria criteria);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<YxUserExtractDto> all, HttpServletResponse response) throws IOException;


    /**
     * 操作提现
     * @param resources YxUserExtract
     */
    void doExtract(YxUserExtract resources);
}
