
package com.jiyi.modules.user.service.mapper;

import com.jiyi.common.mapper.CoreMapper;
import com.jiyi.modules.user.domain.YxUserLevel;
import com.jiyi.modules.user.service.dto.UserLevelInfoDto;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 用户等级记录表 Mapper 接口
 * </p>
 *
 *
 * @since 2019-12-06
 */
@Repository
public interface YxUserLevelMapper extends CoreMapper<YxUserLevel> {

    @Select("SELECT l.id,a.add_time as addTime,l.discount,a.level_id as levelId,l.name," +
            "l.icon,l.grade FROM yx_user_level a INNER JOIN yx_system_user_level l " +
            "ON l.id=a.level_id WHERE a.status = 1 AND a.is_del = 0 AND a.id = #{id} LIMIT 1")
    UserLevelInfoDto getUserLevelInfo(int id);



}
