
package com.jiyi.tools.service.mapper;

import com.jiyi.common.mapper.CoreMapper;
import com.jiyi.tools.domain.Picture;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
*
* @date 2020-05-13
*/
@Repository
@Mapper
public interface PictureMapper extends CoreMapper<Picture> {

}
