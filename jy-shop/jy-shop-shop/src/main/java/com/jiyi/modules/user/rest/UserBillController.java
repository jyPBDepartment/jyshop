
package com.jiyi.modules.user.rest;

import com.jiyi.logging.aop.log.Log;
import com.jiyi.modules.user.service.YxUserBillService;
import com.jiyi.modules.user.service.dto.YxUserBillQueryCriteria;
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
* @date 2019-11-06
*/
@Api(tags = "商城:用户账单管理")
@RestController
@RequestMapping("api")
public class UserBillController {

    private final YxUserBillService yxUserBillService;

    public UserBillController(YxUserBillService yxUserBillService) {
        this.yxUserBillService = yxUserBillService;
    }

    @Log("查询")
    @ApiOperation(value = "查询")
    @GetMapping(value = "/yxUserBill")
    @PreAuthorize("hasAnyRole('admin','YXUSERBILL_ALL','YXUSERBILL_SELECT')")
    public ResponseEntity getYxUserBills(YxUserBillQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(yxUserBillService.queryAll(criteria,pageable),HttpStatus.OK);
    }






}
