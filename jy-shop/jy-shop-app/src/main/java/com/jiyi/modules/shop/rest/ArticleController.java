
package com.jiyi.modules.shop.rest;

import com.jiyi.api.ApiResult;
import com.jiyi.modules.mp.service.YxArticleService;
import com.jiyi.modules.mp.vo.YxArticleQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 文章 前端控制器
 * </p>
 *
 *
 * @since 2019-10-02
 */
@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/article")
@Api(value = "文章模块", tags = "商城:文章模块", description = "文章模块")
public class ArticleController {

    private final YxArticleService articleService;


    /**
    * 获取文章文章详情
    */
    @GetMapping("/details/{id}")
    @ApiOperation(value = "文章详情",notes = "文章详情")
    public ApiResult<YxArticleQueryVo> getYxArticle(@PathVariable Integer id){
        YxArticleQueryVo yxArticleQueryVo = articleService.getDetail(id);
        articleService.incVisitNum(id);
        return ApiResult.ok(yxArticleQueryVo);
    }

    /**
     * 文章列表
     */
    @GetMapping("/list")
    @ApiOperation(value = "文章列表",notes = "文章列表")
    public ApiResult<List<YxArticleQueryVo>> getYxArticlePageList(@RequestParam(value = "page",defaultValue = "1") int page,
                                                                  @RequestParam(value = "limit",defaultValue = "10") int limit){
        return ApiResult.ok(articleService.getList(page,limit));
    }

}

