
package com.jiyi.modules.product.rest;

import com.jiyi.dozer.service.IGenerator;
import com.jiyi.logging.aop.log.Log;
import com.jiyi.modules.aop.ForbidSubmit;
import com.jiyi.modules.product.domain.YxStoreProductRule;
import com.jiyi.modules.product.service.YxStoreProductRuleService;
import com.jiyi.modules.product.service.dto.YxStoreProductRuleDto;
import com.jiyi.modules.product.service.dto.YxStoreProductRuleQueryCriteria;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
*
* @date 2020-06-28
*/
@AllArgsConstructor
@Api(tags = "sku规则管理")
@RestController
@RequestMapping("/api/yxStoreProductRule")
public class StoreProductRuleController {

    private final YxStoreProductRuleService yxStoreProductRuleService;
    private final IGenerator generator;


    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','yxStoreProductRule:list')")
    public void download(HttpServletResponse response, YxStoreProductRuleQueryCriteria criteria) throws IOException {
        yxStoreProductRuleService.download(generator.convert(yxStoreProductRuleService.queryAll(criteria), YxStoreProductRuleDto.class), response);
    }

    @GetMapping
    @Log("查询sku规则")
    @ApiOperation("查询sku规则")
    @PreAuthorize("@el.check('admin','yxStoreProductRule:list')")
    public ResponseEntity<Object> getYxStoreProductRules(YxStoreProductRuleQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(yxStoreProductRuleService.queryAll(criteria,pageable),HttpStatus.OK);
    }


    @PostMapping("/save/{id}")
    @Log("新增/修改sku规则")
    @ApiOperation("新增/修改sku规则")
    @PreAuthorize("hasAnyRole('admin','yxStoreProductRule:add','yxStoreProductRule:edit')")
    public ResponseEntity<Object> create(@Validated @RequestBody YxStoreProductRule resources,@PathVariable Integer id){
        if(id != null && id > 0){
            resources.setId(id);
            yxStoreProductRuleService.updateById(resources);
        }else{
            yxStoreProductRuleService.save(resources);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @ForbidSubmit
    @Log("删除sku规则")
    @ApiOperation("删除sku规则")
    @PreAuthorize("@el.check('admin','yxStoreProductRule:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Integer[] ids) {
        yxStoreProductRuleService.removeByIds(new ArrayList<>(Arrays.asList(ids)));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
