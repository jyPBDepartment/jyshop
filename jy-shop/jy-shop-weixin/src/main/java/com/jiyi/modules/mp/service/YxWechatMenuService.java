
package com.jiyi.modules.mp.service;

import com.jiyi.common.service.BaseService;
import com.jiyi.modules.mp.service.dto.YxWechatMenuDto;
import com.jiyi.modules.mp.service.dto.YxWechatMenuQueryCriteria;
import com.jiyi.modules.mp.domain.YxWechatMenu;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
*
* @date 2020-05-12
*/
public interface YxWechatMenuService  extends BaseService<YxWechatMenu>{

/**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(YxWechatMenuQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<YxWechatMenuDto>
    */
    List<YxWechatMenu> queryAll(YxWechatMenuQueryCriteria criteria);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<YxWechatMenuDto> all, HttpServletResponse response) throws IOException;

    Boolean isExist(String wechat_menus);
}
