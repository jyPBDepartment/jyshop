
package com.jiyi.modules.shop.service.impl;

import com.jiyi.common.service.impl.BaseServiceImpl;
import com.jiyi.common.utils.QueryHelpPlus;
import com.jiyi.dozer.service.IGenerator;
import com.jiyi.modules.shop.domain.YxMaterial;
import com.jiyi.modules.shop.service.YxMaterialService;
import com.jiyi.modules.shop.service.dto.YxMaterialDto;
import com.jiyi.modules.shop.service.dto.YxMaterialQueryCriteria;
import com.jiyi.modules.shop.service.mapper.MaterialMapper;
import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
public class YxMaterialServiceImpl extends BaseServiceImpl<MaterialMapper, YxMaterial> implements YxMaterialService {

    private final IGenerator generator;

    @Override
    //@Cacheable
    public Map<String, Object> queryAll(YxMaterialQueryCriteria criteria, Pageable pageable) {
        getPage(pageable);
        PageInfo<YxMaterial> page = new PageInfo<>(queryAll(criteria));
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", generator.convert(page.getList(), YxMaterialDto.class));
        map.put("totalElements", page.getTotal());
        return map;
    }


    @Override
    //@Cacheable
    public List<YxMaterial> queryAll(YxMaterialQueryCriteria criteria){
        return baseMapper.selectList(QueryHelpPlus.getPredicate(YxMaterial.class, criteria));
    }



}
