
package com.jiyi.modules.template.service.impl;

import com.jiyi.common.service.impl.BaseServiceImpl;
import com.jiyi.common.utils.QueryHelpPlus;
import com.jiyi.dozer.service.IGenerator;
import com.jiyi.modules.template.domain.YxSystemCity;
import com.jiyi.modules.template.service.YxSystemCityService;
import com.jiyi.modules.template.service.dto.YxSystemCityDto;
import com.jiyi.modules.template.service.dto.YxSystemCityQueryCriteria;
import com.jiyi.modules.template.service.mapper.YxSystemCityMapper;
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
* @date 2020-06-29
*/
@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class YxSystemCityServiceImpl extends BaseServiceImpl<YxSystemCityMapper, YxSystemCity> implements YxSystemCityService {

    private final IGenerator generator;

    @Override
    //@Cacheable
    public Map<String, Object> queryAll(YxSystemCityQueryCriteria criteria, Pageable pageable) {
        getPage(pageable);
        PageInfo<YxSystemCity> page = new PageInfo<>(queryAll(criteria));
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", generator.convert(page.getList(), YxSystemCityDto.class));
        map.put("totalElements", page.getTotal());
        return map;
    }


    @Override
    //@Cacheable
    public List<YxSystemCity> queryAll(YxSystemCityQueryCriteria criteria){
        return baseMapper.selectList(QueryHelpPlus.getPredicate(YxSystemCity.class, criteria));
    }


    @Override
    public void download(List<YxSystemCityDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (YxSystemCityDto yxSystemCity : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("城市id", yxSystemCity.getCityId());
            map.put("省市级别", yxSystemCity.getLevel());
            map.put("父级id", yxSystemCity.getParentId());
            map.put("区号", yxSystemCity.getAreaCode());
            map.put("名称", yxSystemCity.getName());
            map.put("合并名称", yxSystemCity.getMergerName());
            map.put("经度", yxSystemCity.getLng());
            map.put("纬度", yxSystemCity.getLat());
            map.put("是否展示", yxSystemCity.getIsShow());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
