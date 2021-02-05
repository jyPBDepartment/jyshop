
package com.jiyi.modules.product.service.impl;

import com.jiyi.common.service.impl.BaseServiceImpl;
import com.jiyi.modules.product.domain.YxStoreProductAttrValue;
import com.jiyi.modules.product.service.YxStoreProductAttrValueService;
import com.jiyi.modules.product.service.mapper.StoreProductAttrValueMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
*
* @date 2020-05-12
*/
@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class YxStoreProductAttrValueServiceImpl extends BaseServiceImpl<StoreProductAttrValueMapper, YxStoreProductAttrValue> implements YxStoreProductAttrValueService {


}
