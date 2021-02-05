
package com.jiyi.modules.system.service.mapper;

import com.jiyi.common.mapper.CoreMapper;
import com.jiyi.modules.system.domain.DictDetail;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
*
* @date 2020-05-14
*/
@Repository
public interface DictDetailMapper extends CoreMapper<DictDetail> {

    @Select("<script>SELECT d.* from dict_detail d LEFT JOIN dict t on d.dict_id = t.id where 1=1 <if test = \"label !=null\" > and d.label LIKE concat('%', #{label}, '%') </if> <if test = \"dictName != ''||dictName !=null\" > AND t.name = #{dictName} order by t.sort asc</if></script>")
    List<DictDetail> selectDictDetailList(@Param("label") String label,@Param("dictName") String dictName);
}
