
package com.jiyi.modules.user.service;


import com.jiyi.common.service.BaseService;
import com.jiyi.modules.user.domain.YxUserAddress;
import com.jiyi.modules.user.param.AddressParam;
import com.jiyi.modules.user.vo.YxUserAddressQueryVo;

import java.util.List;

/**
 * <p>
 * 用户地址表 服务类
 * </p>
 *
 *
 * @since 2019-10-28
 */
public interface YxUserAddressService extends BaseService<YxUserAddress> {

    /**
     * 设置默认地址
     * @param uid uid
     * @param addressId 地址id
     */
    void setDefault(Long uid,Long addressId);

    /**
     * 添加或者修改地址
     * @param uid uid
     * @param param AddressParam
     */
    Long addAndEdit(Long uid, AddressParam param);

    /**
     * 地址详情
     * @param id 地址id
     * @return YxUserAddressQueryVo
     */
    YxUserAddressQueryVo getDetail(Long id);

    /**
     * 获取用户地址
     * @param uid uid
     * @param page page
     * @param limit limit
     * @return List
     */
    List<YxUserAddressQueryVo> getList(Long uid,int page,int limit);

    /**
     * 获取默认地址
     * @param uid uid
     * @return YxUserAddress
     */
    YxUserAddress getUserDefaultAddress(Long uid);

}
