
package com.jiyi.modules.activity.service.impl;

import com.jiyi.common.service.impl.BaseServiceImpl;
import com.jiyi.common.utils.QueryHelpPlus;
import com.jiyi.dozer.service.IGenerator;
import com.jiyi.modules.activity.domain.YxStoreCouponIssueUser;
import com.jiyi.modules.activity.service.YxStoreCouponIssueUserService;
import com.jiyi.modules.activity.service.dto.YxStoreCouponIssueUserDto;
import com.jiyi.modules.activity.service.dto.YxStoreCouponIssueUserQueryCriteria;
import com.jiyi.modules.activity.service.mapper.YxStoreCouponIssueUserMapper;
import com.jiyi.utils.FileUtil;
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
* @date 2020-05-13
*/
@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class YxStoreCouponIssueUserServiceImpl extends BaseServiceImpl<YxStoreCouponIssueUserMapper, YxStoreCouponIssueUser> implements YxStoreCouponIssueUserService {

    private final IGenerator generator;

    /**
     * 添加优惠券领取记录
     * @param uid 用户id
     * @param id 前台优惠券id
     */
    @Override
    public void addUserIssue(Long uid, Integer id) {
        YxStoreCouponIssueUser couponIssueUser = new YxStoreCouponIssueUser();
        couponIssueUser.setIssueCouponId(id);
        couponIssueUser.setUid(uid);
        this.save(couponIssueUser);
    }


    @Override
    //@Cacheable
    public Map<String, Object> queryAll(YxStoreCouponIssueUserQueryCriteria criteria, Pageable pageable) {
        getPage(pageable);
        PageInfo<YxStoreCouponIssueUser> page = new PageInfo<>(queryAll(criteria));
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", generator.convert(page.getList(), YxStoreCouponIssueUserDto.class));
        map.put("totalElements", page.getTotal());
        return map;
    }


    @Override
    //@Cacheable
    public List<YxStoreCouponIssueUser> queryAll(YxStoreCouponIssueUserQueryCriteria criteria){
        return baseMapper.selectList(QueryHelpPlus.getPredicate(YxStoreCouponIssueUser.class, criteria));
    }


    @Override
    public void download(List<YxStoreCouponIssueUserDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (YxStoreCouponIssueUserDto yxStoreCouponIssueUser : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("领取优惠券用户ID", yxStoreCouponIssueUser.getUid());
            map.put("优惠券前台领取ID", yxStoreCouponIssueUser.getIssueCouponId());
            map.put("领取时间", yxStoreCouponIssueUser.getAddTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
