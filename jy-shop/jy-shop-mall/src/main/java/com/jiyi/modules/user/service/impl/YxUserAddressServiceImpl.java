
package com.jiyi.modules.user.service.impl;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.jiyi.common.service.impl.BaseServiceImpl;
import com.jiyi.dozer.service.IGenerator;
import com.jiyi.enums.ShopCommonEnum;
import com.jiyi.modules.user.domain.YxUserAddress;
import com.jiyi.modules.user.param.AddressParam;
import com.jiyi.modules.user.service.YxUserAddressService;
import com.jiyi.modules.user.service.mapper.YxUserAddressMapper;
import com.jiyi.modules.user.vo.YxUserAddressQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * <p>
 * 用户地址表 服务实现类
 * </p>
 *
 *
 * @since 2019-10-28
 */
@Slf4j
@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class YxUserAddressServiceImpl extends BaseServiceImpl<YxUserAddressMapper, YxUserAddress> implements YxUserAddressService {

    private final YxUserAddressMapper yxUserAddressMapper;
    private final IGenerator generator;

    /**
     * 设置默认地址
     * @param uid uid
     * @param addressId 地址id
     */
    @Override
    public void setDefault(Long uid,Long addressId){
        YxUserAddress address = new YxUserAddress();
        address.setIsDefault(ShopCommonEnum.DEFAULT_0.getValue());
        yxUserAddressMapper.update(address,
                new LambdaQueryWrapper<YxUserAddress>().eq(YxUserAddress::getUid,uid));

        YxUserAddress userAddress = new YxUserAddress();
        userAddress.setIsDefault(ShopCommonEnum.DEFAULT_1.getValue());
        userAddress.setId(addressId);
        yxUserAddressMapper.updateById(userAddress);
    }


    /**
     * 添加或者修改地址
     * @param uid uid
     * @param param AddressParam
     */
    @Override
    public Long addAndEdit(Long uid, AddressParam param){
        YxUserAddress userAddress = YxUserAddress.builder()
                .city(param.getAddress().getCity())
                .cityId(param.getAddress().getCityId())
                .district(param.getAddress().getDistrict())
                .province(param.getAddress().getProvince())
                .detail(param.getDetail())
                .uid(uid)
                .phone(param.getPhone())
                .postCode(param.getPost_code())
                .realName(param.getReal_name())
                .build();
        if("true".equals(param.getIs_default())){
            userAddress.setIsDefault(ShopCommonEnum.DEFAULT_1.getValue());
            //新增地址如果是默认，把之前的状态改掉
            YxUserAddress address = new YxUserAddress();
            address.setIsDefault(ShopCommonEnum.DEFAULT_0.getValue());
            baseMapper.update(address,new LambdaQueryWrapper<YxUserAddress>().eq(YxUserAddress::getUid,uid));
        }else{
            userAddress.setIsDefault(ShopCommonEnum.DEFAULT_0.getValue());
        }
        if(StrUtil.isBlank(param.getId())){
            this.save(userAddress);
        }else{
            userAddress.setId(Long.valueOf(param.getId()));
            this.updateById(userAddress);
        }

        return userAddress.getId();
    }

    /**
     * 地址详情
     * @param id 地址id
     * @return YxUserAddressQueryVo
     */
    @Override
    public YxUserAddressQueryVo getDetail(Long id){
        return generator.convert(this.getById(id),YxUserAddressQueryVo.class);
    }


    /**
     * 获取用户地址
     * @param uid uid
     * @param page page
     * @param limit limit
     * @return List
     */
    @Override
    public List<YxUserAddressQueryVo> getList(Long uid,int page,int limit){
        Page<YxUserAddress> pageModel = new Page<>(page, limit);
        IPage<YxUserAddress> pageList = this.lambdaQuery().eq(YxUserAddress::getUid,uid).page(pageModel);
        return generator.convert(pageList.getRecords(),YxUserAddressQueryVo.class);
    }

    /**
     * 获取默认地址
     * @param uid uid
     * @return YxUserAddress
     */
    @Override
    public YxUserAddress getUserDefaultAddress(Long uid) {
        LambdaQueryWrapper<YxUserAddress> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(YxUserAddress::getIsDefault,1).
                eq(YxUserAddress::getUid,uid)
                .last("limit 1");
        return getOne(wrapper);
    }



}
