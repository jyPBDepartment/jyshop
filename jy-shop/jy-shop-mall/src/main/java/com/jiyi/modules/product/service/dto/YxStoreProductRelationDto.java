
package com.jiyi.modules.product.service.dto;

import com.jiyi.modules.product.domain.YxStoreProduct;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.io.Serializable;

/**
 *
 * @date 2020-09-03
 */
@Data
public class YxStoreProductRelationDto implements Serializable {

    private Long id;

    /** 用户ID */
    private Long uid;

    private String userName;

    /** 商品ID */
    private Long productId;

    private YxStoreProduct product;

    /** 类型(收藏(collect）、点赞(like)) */
    private String type;

    /** 某种类型的商品(普通商品、秒杀商品) */
    private String category;

    /** 添加时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp updateTime;

    private Integer isDel;
}

