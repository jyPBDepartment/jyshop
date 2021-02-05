
package com.jiyi.gen.service.mapper;

import com.jiyi.common.mapper.CoreMapper;
import com.jiyi.gen.domain.GenConfig;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface GenConfigMapper extends CoreMapper<GenConfig> {
}
