
package com.jiyi.modules.order.service.impl;

import cn.hutool.core.util.IdUtil;
import com.jiyi.common.service.impl.BaseServiceImpl;
import com.jiyi.common.utils.QueryHelpPlus;
import com.jiyi.dozer.service.IGenerator;
import com.jiyi.modules.cart.vo.YxStoreCartQueryVo;
import com.jiyi.modules.order.domain.YxStoreOrderCartInfo;
import com.jiyi.modules.order.service.YxStoreOrderCartInfoService;
import com.jiyi.modules.order.service.dto.YxStoreOrderCartInfoDto;
import com.jiyi.modules.order.service.dto.YxStoreOrderCartInfoQueryCriteria;
import com.jiyi.modules.order.service.mapper.StoreOrderCartInfoMapper;
import com.jiyi.utils.FileUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
public class YxStoreOrderCartInfoServiceImpl extends BaseServiceImpl<StoreOrderCartInfoMapper, YxStoreOrderCartInfo> implements YxStoreOrderCartInfoService {

    private final IGenerator generator;

    @Override
    public YxStoreOrderCartInfo findByUni(String unique) {
       LambdaQueryWrapper<YxStoreOrderCartInfo> wrapper= new LambdaQueryWrapper<>();
        wrapper.eq(YxStoreOrderCartInfo::getUnique,unique);
        return this.baseMapper.selectOne(wrapper);
    }

    /**
     * 添加购物车商品信息
     * @param oid 订单id
     * @param cartInfo 购物车信息
     */
    @Override
    public void saveCartInfo(Long oid, List<YxStoreCartQueryVo> cartInfo) {

        List<YxStoreOrderCartInfo> list = new ArrayList<>();
        for (YxStoreCartQueryVo cart : cartInfo) {
            YxStoreOrderCartInfo info = new YxStoreOrderCartInfo();
            info.setOid(oid);
            info.setCartId(cart.getId());
            info.setProductId(cart.getProductId());
            info.setCartInfo(JSONObject.toJSON(cart).toString());
            info.setUnique(IdUtil.simpleUUID());
            list.add(info);
        }

        this.saveBatch(list);
    }


    @Override
    //@Cacheable
    public Map<String, Object> queryAll(YxStoreOrderCartInfoQueryCriteria criteria, Pageable pageable) {
        getPage(pageable);
        PageInfo<YxStoreOrderCartInfo> page = new PageInfo<>(queryAll(criteria));
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", generator.convert(page.getList(), YxStoreOrderCartInfoDto.class));
        map.put("totalElements", page.getTotal());
        return map;
    }


    @Override
    //@Cacheable
    public List<YxStoreOrderCartInfo> queryAll(YxStoreOrderCartInfoQueryCriteria criteria){
        return baseMapper.selectList(QueryHelpPlus.getPredicate(YxStoreOrderCartInfo.class, criteria));
    }


    @Override
    public void download(List<YxStoreOrderCartInfoDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (YxStoreOrderCartInfoDto yxStoreOrderCartInfo : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("订单id", yxStoreOrderCartInfo.getOid());
            map.put("购物车id", yxStoreOrderCartInfo.getCartId());
            map.put("商品ID", yxStoreOrderCartInfo.getProductId());
            map.put("购买东西的详细信息", yxStoreOrderCartInfo.getCartInfo());
            map.put("唯一id", yxStoreOrderCartInfo.getUnique());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
