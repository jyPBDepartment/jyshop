
package com.jiyi.modules.activity.service.impl;

import cn.hutool.core.util.StrUtil;
import com.jiyi.common.service.impl.BaseServiceImpl;
import com.jiyi.common.utils.QueryHelpPlus;
import com.jiyi.dozer.service.IGenerator;
import com.jiyi.modules.activity.domain.YxStoreCoupon;
import com.jiyi.modules.activity.service.YxStoreCouponService;
import com.jiyi.modules.activity.service.dto.YxStoreCouponDto;
import com.jiyi.modules.activity.service.dto.YxStoreCouponQueryCriteria;
import com.jiyi.modules.activity.service.mapper.YxStoreCouponMapper;
import com.jiyi.modules.product.domain.YxStoreProduct;
import com.jiyi.modules.product.service.YxStoreProductService;
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
import java.util.Arrays;
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
public class YxStoreCouponServiceImpl extends BaseServiceImpl<YxStoreCouponMapper, YxStoreCoupon> implements YxStoreCouponService {

    private final IGenerator generator;
    private final YxStoreProductService storeProductService;

    @Override
    //@Cacheable
    public Map<String, Object> queryAll(YxStoreCouponQueryCriteria criteria, Pageable pageable) {
        getPage(pageable);
        PageInfo<YxStoreCoupon> page = new PageInfo<>(queryAll(criteria));
        Map<String, Object> map = new LinkedHashMap<>(2);
        List<YxStoreCouponDto> storeCouponDtos = generator.convert(page.getList(), YxStoreCouponDto.class);
        for (YxStoreCouponDto storeCouponDto : storeCouponDtos) {
            if(StrUtil.isNotBlank(storeCouponDto.getProductId())){
                List<YxStoreProduct> storeProducts = storeProductService.lambdaQuery()
                        .in(YxStoreProduct::getId, Arrays.asList(storeCouponDto.getProductId().split(",")))
                        .list();
                storeCouponDto.setProduct(storeProducts);
            }
        }
        map.put("content", storeCouponDtos);
        map.put("totalElements", page.getTotal());
        return map;
    }


    @Override
    //@Cacheable
    public List<YxStoreCoupon> queryAll(YxStoreCouponQueryCriteria criteria){
        return baseMapper.selectList(QueryHelpPlus.getPredicate(YxStoreCoupon.class, criteria));
    }


    @Override
    public void download(List<YxStoreCouponDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (YxStoreCouponDto yxStoreCoupon : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("优惠券名称", yxStoreCoupon.getTitle());
            map.put("兑换消耗积分值", yxStoreCoupon.getIntegral());
            map.put("兑换的优惠券面值", yxStoreCoupon.getCouponPrice());
            map.put("最低消费多少金额可用优惠券", yxStoreCoupon.getUseMinPrice());
            map.put("优惠券有效期限（单位：天）", yxStoreCoupon.getCouponTime());
            map.put("排序", yxStoreCoupon.getSort());
            map.put("状态（0：关闭，1：开启）", yxStoreCoupon.getStatus());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
