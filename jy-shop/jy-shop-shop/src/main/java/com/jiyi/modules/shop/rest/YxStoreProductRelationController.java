
package com.jiyi.modules.shop.rest;
import java.util.Arrays;
import com.jiyi.dozer.service.IGenerator;
import com.jiyi.modules.aop.ForbidSubmit;
import lombok.AllArgsConstructor;
import com.jiyi.logging.aop.log.Log;
import com.jiyi.modules.product.domain.YxStoreProductRelation;
import com.jiyi.modules.product.service.YxStoreProductRelationService;
import com.jiyi.modules.product.service.dto.YxStoreProductRelationQueryCriteria;
import com.jiyi.modules.product.service.dto.YxStoreProductRelationDto;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import com.jiyi.domain.PageResult;
/**
 *
 * @date 2020-09-03
 */
@AllArgsConstructor
@Api(tags = "ProductRelation管理")
@RestController
@RequestMapping("/api/yxStoreProductRelation")
public class YxStoreProductRelationController {

    private final YxStoreProductRelationService yxStoreProductRelationService;
    private final IGenerator generator;


    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','yxStoreProductRelation:list')")
    public void download(HttpServletResponse response, YxStoreProductRelationQueryCriteria criteria) throws IOException {
        yxStoreProductRelationService.download(generator.convert(yxStoreProductRelationService.queryAll(criteria), YxStoreProductRelationDto.class), response);
    }

    @GetMapping
    @Log("查询ProductRelation")
    @ApiOperation("查询ProductRelation")
    @PreAuthorize("@el.check('admin','yxStoreProductRelation:list')")
    public ResponseEntity<PageResult<YxStoreProductRelationDto>> getYxStoreProductRelations(YxStoreProductRelationQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(yxStoreProductRelationService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增ProductRelation")
    @ApiOperation("新增ProductRelation")
    @PreAuthorize("@el.check('admin','yxStoreProductRelation:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody YxStoreProductRelation resources){
        return new ResponseEntity<>(yxStoreProductRelationService.save(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改ProductRelation")
    @ApiOperation("修改ProductRelation")
    @PreAuthorize("@el.check('admin','yxStoreProductRelation:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody YxStoreProductRelation resources){
        yxStoreProductRelationService.updateById(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ForbidSubmit
    @Log("删除ProductRelation")
    @ApiOperation("删除ProductRelation")
    @PreAuthorize("@el.check('admin','yxStoreProductRelation:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        Arrays.asList(ids).forEach(id->{
            yxStoreProductRelationService.removeById(id);
        });
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
