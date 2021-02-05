
package com.jiyi.api;

/**
 * 认证异常
 *
 * @date 2020-04-30
 */
public class UnAuthenticatedException extends YshopException {
    public UnAuthenticatedException(String message) {
        super(message);
    }

    public UnAuthenticatedException(Integer errorCode, String message) {
        super(errorCode, message);
    }

    public UnAuthenticatedException(ApiCode apiCode) {
        super(apiCode);
    }
}
