
package com.jiyi.modules.activity.rest;

import cn.hutool.core.util.ObjectUtil;
import com.jiyi.api.YshopException;
import com.jiyi.logging.aop.log.Log;
import com.jiyi.modules.activity.domain.YxStoreBargain;
import com.jiyi.modules.activity.service.YxStoreBargainService;
import com.jiyi.modules.activity.service.dto.YxStoreBargainQueryCriteria;
import com.jiyi.modules.aop.ForbidSubmit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
*
* @date 2019-12-22
*/
@Api(tags = "商城:砍价管理")
@RestController
@RequestMapping("api")
public class StoreBargainController {

    private final YxStoreBargainService yxStoreBargainService;

    public StoreBargainController(YxStoreBargainService yxStoreBargainService) {
        this.yxStoreBargainService = yxStoreBargainService;
    }

    @Log("查询砍价")
    @ApiOperation(value = "查询砍价")
    @GetMapping(value = "/yxStoreBargain")
    @PreAuthorize("hasAnyRole('admin','YXSTOREBARGAIN_ALL','YXSTOREBARGAIN_SELECT')")
    public ResponseEntity getYxStoreBargains(YxStoreBargainQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(yxStoreBargainService.queryAll(criteria,pageable),HttpStatus.OK);
    }



    @Log("修改砍价")
    @ApiOperation(value = "修改砍价")
    @PutMapping(value = "/yxStoreBargain")
    @PreAuthorize("hasAnyRole('admin','YXSTOREBARGAIN_ALL','YXSTOREBARGAIN_EDIT')")
    public ResponseEntity update(@Validated @RequestBody YxStoreBargain resources){
        if(resources.getBargainMinPrice().compareTo(resources.getBargainMaxPrice()) >= 0){
            throw new YshopException("单次砍最低价不能高于单次砍最高价");
        }
        if(resources.getMinPrice().compareTo(resources.getPrice()) >= 0){
            throw new YshopException("允许砍到最低价不能高于砍价金额");
        }
        if(ObjectUtil.isNull(resources.getId())){
            return new ResponseEntity<>(yxStoreBargainService.save(resources),HttpStatus.CREATED);
        }else{
            yxStoreBargainService.saveOrUpdate(resources);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    }

    @ForbidSubmit
    @Log("删除砍价")
    @ApiOperation(value = "删除砍价")
    @DeleteMapping(value = "/yxStoreBargain/{id}")
    @PreAuthorize("hasAnyRole('admin','YXSTOREBARGAIN_ALL','YXSTOREBARGAIN_DELETE')")
    public ResponseEntity delete(@PathVariable Integer id){
        yxStoreBargainService.removeById(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
