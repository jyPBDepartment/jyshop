
package com.jiyi.modules.mp.service.impl;

import com.jiyi.common.service.impl.BaseServiceImpl;
import com.jiyi.common.utils.QueryHelpPlus;
import com.jiyi.dozer.service.IGenerator;
import com.jiyi.modules.mp.service.mapper.ArticleMapper;
import com.jiyi.modules.mp.domain.YxArticle;
import com.jiyi.modules.mp.service.YxArticleService;
import com.jiyi.modules.mp.service.dto.YxArticleDto;
import com.jiyi.modules.mp.service.dto.YxArticleQueryCriteria;
import com.jiyi.modules.mp.vo.YxArticleQueryVo;
import com.jiyi.utils.FileUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
@Slf4j
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class YxArticleServiceImpl extends BaseServiceImpl<ArticleMapper, YxArticle> implements YxArticleService {

    private final IGenerator generator;
    private final ArticleMapper articleMapper;
    @Value("${file.path}")
    private String uploadDirStr;

    public YxArticleServiceImpl(IGenerator generator,ArticleMapper articleMapper) {
        this.generator = generator;
        this.articleMapper = articleMapper;
    }

    /**
     * 获取文章列表
     * @param page 页码
     * @param limit 条数
     * @return List
     */
    @Override
    public List<YxArticleQueryVo> getList(int page, int limit){
        Page<YxArticle> pageModel = new Page<>(page, limit);

        IPage<YxArticle> pageList = articleMapper.selectPage(pageModel, Wrappers.<YxArticle>lambdaQuery()
                .orderByDesc(YxArticle::getId));

        return generator.convert(pageList.getRecords(),YxArticleQueryVo.class);
    }

    /**
     * 获取文章详情
     * @param id id
     * @return YxArticleQueryVo
     */
    @Override
    public YxArticleQueryVo getDetail(int id){
        return generator.convert(this.getById(id),YxArticleQueryVo.class);
    }


    @Override
    public void incVisitNum(int id) {
        articleMapper.incVisitNum(id);
    }

    @Override
    //@Cacheable
    public Map<String, Object> queryAll(YxArticleQueryCriteria criteria, Pageable pageable) {
        getPage(pageable);
        PageInfo<YxArticle> page = new PageInfo<>(queryAll(criteria));
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", generator.convert(page.getList(), YxArticleDto.class));
        map.put("totalElements", page.getTotal());
        return map;
    }


    @Override
    //@Cacheable
    public List<YxArticle> queryAll(YxArticleQueryCriteria criteria){
        return baseMapper.selectList(QueryHelpPlus.getPredicate(YxArticle.class, criteria));
    }


    @Override
    public void download(List<YxArticleDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (YxArticleDto yxArticle : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("分类id", yxArticle.getCid());
            map.put("文章标题", yxArticle.getTitle());
            map.put("文章作者", yxArticle.getAuthor());
            map.put("文章图片", yxArticle.getImageInput());
            map.put("文章简介", yxArticle.getSynopsis());
            map.put(" content",  yxArticle.getContent());
            map.put("文章分享标题", yxArticle.getShareTitle());
            map.put("文章分享简介", yxArticle.getShareSynopsis());
            map.put("浏览次数", yxArticle.getVisit());
            map.put("排序", yxArticle.getSort());
            map.put("原文链接", yxArticle.getUrl());
            map.put("状态", yxArticle.getStatus());
            map.put("是否隐藏", yxArticle.getHide());
            map.put("管理员id", yxArticle.getAdminId());
            map.put("商户id", yxArticle.getMerId());
            map.put("产品关联id", yxArticle.getProductId());
            map.put("是否热门(小程序)", yxArticle.getIsHot());
            map.put("是否轮播图(小程序)", yxArticle.getIsBanner());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }




}
