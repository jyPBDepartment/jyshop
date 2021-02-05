
package com.jiyi.modules.order.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
*
* @date 2020-05-12
*/
@Data
public class YxStoreOrderStatusDto implements Serializable {

    private Integer id;

    /** 订单id */
    private Integer oid;

    /** 操作类型 */
    private String changeType;

    /** 操作备注 */
    private String changeMessage;

    /** 操作时间 */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date changeTime;
}
