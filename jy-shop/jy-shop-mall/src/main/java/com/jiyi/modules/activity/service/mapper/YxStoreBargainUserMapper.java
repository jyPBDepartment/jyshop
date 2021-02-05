
package com.jiyi.modules.activity.service.mapper;


import com.jiyi.common.mapper.CoreMapper;
import com.jiyi.modules.activity.domain.YxStoreBargainUser;
import com.jiyi.modules.activity.vo.YxStoreBargainUserQueryVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 用户参与砍价表 Mapper 接口
 * </p>
 *
 *
 * @since 2019-12-21
 */
@Repository
public interface YxStoreBargainUserMapper extends CoreMapper<YxStoreBargainUser> {


    @Select("SELECT u.uid,u.is_del as isDel,u.bargain_price - u.price as residuePrice,u.id," +
            "u.bargain_id as bargainId,u.bargain_price as bargainPrice," +
            "u.bargain_price_min as bargainPriceMin,u.price,u.status,b.title," +
            "b.image,b.stop_time as datatime FROM yx_store_bargain_user u INNER JOIN " +
            "yx_store_bargain b ON b.id=u.bargain_id WHERE u.uid = #{uid} AND u.is_del = 0 " +
            "ORDER BY u.id DESC ")
    List<YxStoreBargainUserQueryVo> getBargainUserList(@Param("uid") Long uid, Page page);


}
