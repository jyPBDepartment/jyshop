
package com.jiyi.common.bean;


import com.jiyi.modules.user.domain.YxUser;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局user
 *
 * @date 2020-04-30
 */
public class LocalUser {
    private static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<>();

    public static void set(YxUser user, Integer scope) {
        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        map.put("scope", scope);
        LocalUser.threadLocal.set(map);
    }

    public static void clear() {
        LocalUser.threadLocal.remove();
    }

    public static YxUser getUser() {
        Map<String, Object> map = LocalUser.threadLocal.get();
        YxUser user = (YxUser)map.get("user");
        return user;
    }

    public static Integer getScope() {
        Map<String, Object> map = LocalUser.threadLocal.get();
        Integer scope = (Integer)map.get("scope");
        return scope;
    }
}
