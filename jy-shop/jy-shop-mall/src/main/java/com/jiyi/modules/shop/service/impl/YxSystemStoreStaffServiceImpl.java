
package com.jiyi.modules.shop.service.impl;

import com.jiyi.common.service.impl.BaseServiceImpl;
import com.jiyi.common.utils.QueryHelpPlus;
import com.jiyi.dozer.service.IGenerator;
import com.jiyi.enums.ShopCommonEnum;
import com.jiyi.modules.shop.domain.YxSystemStoreStaff;
import com.jiyi.modules.shop.service.YxSystemStoreStaffService;
import com.jiyi.modules.shop.service.dto.YxSystemStoreStaffDto;
import com.jiyi.modules.shop.service.dto.YxSystemStoreStaffQueryCriteria;
import com.jiyi.modules.shop.service.mapper.SystemStoreStaffMapper;
import com.jiyi.utils.FileUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;



/**
*
* @date 2020-05-12
*/
@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class YxSystemStoreStaffServiceImpl extends BaseServiceImpl<SystemStoreStaffMapper, YxSystemStoreStaff> implements YxSystemStoreStaffService {

    private final IGenerator generator;


    /**
     * 接测店员客服状态
     * @param uid 用户id
     * @param storeId 门店id
     * @return boolean true=可核销
     */
    @Override
    public boolean checkStatus(Long uid,Integer storeId) {
        YxSystemStoreStaff storeStaff = new YxSystemStoreStaff();
        storeStaff.setUid(uid);
        storeStaff.setVerifyStatus(ShopCommonEnum.IS_STATUS_1.getValue());
        if(storeId != null) {
            storeStaff.setStoreId(storeId);
        }
        return this.baseMapper.selectCount(Wrappers.query(storeStaff)) > 0;
    }

    @Override
    //@Cacheable
    public Map<String, Object> queryAll(YxSystemStoreStaffQueryCriteria criteria, Pageable pageable) {
        getPage(pageable);
        PageInfo<YxSystemStoreStaff> page = new PageInfo<>(queryAll(criteria));
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", generator.convert(page.getList(), YxSystemStoreStaffDto.class));
        map.put("totalElements", page.getTotal());
        return map;
    }


    @Override
    //@Cacheable
    public List<YxSystemStoreStaff> queryAll(YxSystemStoreStaffQueryCriteria criteria){
        return baseMapper.selectList(QueryHelpPlus.getPredicate(YxSystemStoreStaff.class, criteria));
    }


    @Override
    public void download(List<YxSystemStoreStaffDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (YxSystemStoreStaffDto yxSystemStoreStaff : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("微信用户id", yxSystemStoreStaff.getUid());
            map.put("店员头像", yxSystemStoreStaff.getAvatar());
            map.put("门店id", yxSystemStoreStaff.getStoreId());
            map.put("店员名称", yxSystemStoreStaff.getStaffName());
            map.put("手机号码", yxSystemStoreStaff.getPhone());
            map.put("核销开关", yxSystemStoreStaff.getVerifyStatus());
            map.put("状态", yxSystemStoreStaff.getStatus());
            map.put("微信昵称", yxSystemStoreStaff.getNickname());
            map.put("所属门店", yxSystemStoreStaff.getStoreName());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
