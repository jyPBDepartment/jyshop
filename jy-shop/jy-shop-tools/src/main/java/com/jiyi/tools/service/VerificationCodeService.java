
package com.jiyi.tools.service;

import com.jiyi.tools.domain.VerificationCode;
import com.jiyi.tools.domain.vo.EmailVo;

/**
 *
 * @date 2018-12-26
 */
public interface VerificationCodeService {

    /**
     * 发送邮件验证码
     * @param code 验证码
     * @return EmailVo
     */
    EmailVo sendEmail(VerificationCode code);

    /**
     * 验证
     * @param code 验证码
     */
    void validated(VerificationCode code);
}
