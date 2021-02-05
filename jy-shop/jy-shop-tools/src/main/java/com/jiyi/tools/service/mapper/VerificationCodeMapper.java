
package com.jiyi.tools.service.mapper;


import com.jiyi.common.mapper.CoreMapper;
import com.jiyi.tools.domain.VerificationCode;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface VerificationCodeMapper extends CoreMapper<VerificationCode> {

}
