
package com.jiyi.modules.activity.rest;

import com.jiyi.logging.aop.log.Log;
import com.jiyi.modules.activity.service.YxStorePinkService;
import com.jiyi.modules.activity.service.dto.YxStorePinkQueryCriteria;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
*
* @date 2019-11-18
*/
@Api(tags = "商城:拼团记录管理")
@RestController
@RequestMapping("api")
public class StorePinkController {

    private final YxStorePinkService yxStorePinkService;

    public StorePinkController(YxStorePinkService yxStorePinkService) {
        this.yxStorePinkService = yxStorePinkService;
    }

    @Log("查询记录")
    @ApiOperation(value = "查询记录")
    @GetMapping(value = "/yxStorePink")
    @PreAuthorize("@el.check('admin','YXSTOREPINK_ALL','YXSTOREPINK_SELECT')")
    public ResponseEntity getYxStorePinks(YxStorePinkQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(yxStorePinkService.queryAll(criteria,pageable),HttpStatus.OK);
    }


}
