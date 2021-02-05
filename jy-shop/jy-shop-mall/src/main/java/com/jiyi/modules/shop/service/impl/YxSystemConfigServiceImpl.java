
package com.jiyi.modules.shop.service.impl;

import cn.hutool.core.util.StrUtil;
import com.jiyi.common.service.impl.BaseServiceImpl;
import com.jiyi.common.utils.QueryHelpPlus;
import com.jiyi.dozer.service.IGenerator;
import com.jiyi.modules.shop.domain.YxSystemConfig;
import com.jiyi.modules.shop.service.YxSystemConfigService;
import com.jiyi.modules.shop.service.dto.YxSystemConfigDto;
import com.jiyi.modules.shop.service.dto.YxSystemConfigQueryCriteria;
import com.jiyi.modules.shop.service.mapper.SystemConfigMapper;
import com.jiyi.utils.FileUtil;
import com.jiyi.utils.RedisUtils;
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
public class YxSystemConfigServiceImpl extends BaseServiceImpl<SystemConfigMapper, YxSystemConfig> implements YxSystemConfigService {

    private final IGenerator generator;
    private final RedisUtils redisUtils;

    /**
     * 获取配置值
     * @param name 配置名
     * @return string
     */
    @Override
    public String getData(String name) {
        String result = redisUtils.getY(name);
        if(StrUtil.isNotBlank(result)) {
            return result;
        }

       LambdaQueryWrapper<YxSystemConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(YxSystemConfig::getMenuName,name);
        YxSystemConfig systemConfig = this.baseMapper.selectOne(wrapper);
        if(systemConfig == null) {
            return "";
        }
        return systemConfig.getValue();
    }

    @Override
    //@Cacheable
    public Map<String, Object> queryAll(YxSystemConfigQueryCriteria criteria, Pageable pageable) {
        getPage(pageable);
        PageInfo<YxSystemConfig> page = new PageInfo<>(queryAll(criteria));
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", generator.convert(page.getList(), YxSystemConfigDto.class));
        map.put("totalElements", page.getTotal());
        return map;
    }


    @Override
    //@Cacheable
    public List<YxSystemConfig> queryAll(YxSystemConfigQueryCriteria criteria){
        return baseMapper.selectList(QueryHelpPlus.getPredicate(YxSystemConfig.class, criteria));
    }


    @Override
    public void download(List<YxSystemConfigDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (YxSystemConfigDto yxSystemConfig : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("字段名称", yxSystemConfig.getMenuName());
            map.put("默认值", yxSystemConfig.getValue());
            map.put("排序", yxSystemConfig.getSort());
            map.put("是否隐藏", yxSystemConfig.getStatus());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public YxSystemConfig findByKey(String key) {
        return this.getOne(new LambdaQueryWrapper<YxSystemConfig>()
                .eq(YxSystemConfig::getMenuName,key));
    }
}
