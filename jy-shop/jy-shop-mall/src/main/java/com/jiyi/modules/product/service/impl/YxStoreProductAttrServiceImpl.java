
package com.jiyi.modules.product.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.jiyi.api.BusinessException;
import com.jiyi.api.YshopException;
import com.jiyi.common.service.impl.BaseServiceImpl;
import com.jiyi.dozer.service.IGenerator;
import com.jiyi.enums.ProductTypeEnum;
import com.jiyi.exception.BadRequestException;
import com.jiyi.modules.product.domain.YxStoreProductAttr;
import com.jiyi.modules.product.domain.YxStoreProductAttrValue;
import com.jiyi.modules.product.service.YxStoreProductAttrResultService;
import com.jiyi.modules.product.service.YxStoreProductAttrService;
import com.jiyi.modules.product.service.YxStoreProductAttrValueService;
import com.jiyi.modules.product.service.dto.AttrValueDto;
import com.jiyi.modules.product.service.dto.FromatDetailDto;
import com.jiyi.modules.product.service.dto.ProductFormatDto;
import com.jiyi.modules.product.service.mapper.StoreProductAttrMapper;
import com.jiyi.modules.product.service.mapper.StoreProductAttrValueMapper;
import com.jiyi.modules.product.vo.YxStoreProductAttrQueryVo;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
*
* @date 2020-05-12
*/
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class YxStoreProductAttrServiceImpl extends BaseServiceImpl<StoreProductAttrMapper, YxStoreProductAttr> implements YxStoreProductAttrService {

    @Autowired
    private IGenerator generator;

    @Autowired
    private StoreProductAttrMapper yxStoreProductAttrMapper;
    @Autowired
    private StoreProductAttrValueMapper yxStoreProductAttrValueMapper;

    @Autowired
    private YxStoreProductAttrValueService storeProductAttrValueService;
    @Autowired
    private YxStoreProductAttrResultService storeProductAttrResultService;

    /**
     * 新增商品属性
     * @param items attr
     * @param attrs value
     * @param productId 商品id
     */
    @Override
    @Transactional
    public void insertYxStoreProductAttr(List<FromatDetailDto> items, List<ProductFormatDto> attrs,
                                         Long productId)
    {
        List<YxStoreProductAttr> attrGroup = new ArrayList<>();
        for (FromatDetailDto fromatDetailDto : items) {
            YxStoreProductAttr  yxStoreProductAttr = YxStoreProductAttr.builder()
                    .productId(productId)
                    .attrName(fromatDetailDto.getValue())
                    .attrValues(StrUtil.join(",",fromatDetailDto.getDetail()))
                    .build();

            attrGroup.add(yxStoreProductAttr);
        }


        List<YxStoreProductAttrValue> valueGroup = new ArrayList<>();
        for (ProductFormatDto productFormatDto : attrs) {

            if(productFormatDto.getPinkStock()>productFormatDto.getStock() || productFormatDto.getSeckillStock()>productFormatDto.getStock()){
                throw new BadRequestException("活动商品库存不能大于原有商品库存");
            }
            List<String> stringList = new ArrayList<>(productFormatDto.getDetail().values());
            Collections.sort(stringList);

            YxStoreProductAttrValue yxStoreProductAttrValue = YxStoreProductAttrValue.builder()
                    .productId(productId)
                    .sku(StrUtil.join(",",stringList))
                    .price(BigDecimal.valueOf(productFormatDto.getPrice()))
                    .cost(BigDecimal.valueOf(productFormatDto.getCost()))
                    .otPrice(BigDecimal.valueOf(productFormatDto.getOtPrice()))
                    .unique(IdUtil.simpleUUID())
                    .image(productFormatDto.getPic())
                    .barCode(productFormatDto.getBarCode())
                    .weight(BigDecimal.valueOf(productFormatDto.getWeight()))
                    .volume(BigDecimal.valueOf(productFormatDto.getVolume()))
                    .brokerage(BigDecimal.valueOf(productFormatDto.getBrokerage()))
                    .brokerageTwo(BigDecimal.valueOf(productFormatDto.getBrokerageTwo()))
                    .stock(productFormatDto.getStock())
                    .pinkPrice(BigDecimal.valueOf(productFormatDto.getPinkPrice()==null?0:productFormatDto.getPinkPrice()))
                    .seckillPrice(BigDecimal.valueOf(productFormatDto.getSeckillPrice()==null?0:productFormatDto.getSeckillPrice()))
                    .pinkStock(productFormatDto.getPinkStock()==null?0:productFormatDto.getPinkStock())
                    .seckillStock(productFormatDto.getSeckillStock()==null?0:productFormatDto.getSeckillStock())
                    .build();


            valueGroup.add(yxStoreProductAttrValue);
        }

        if(attrGroup.isEmpty() || valueGroup.isEmpty()){
            throw new BusinessException("请设置至少一个属性!");
        }

        //清理属性
        this.clearProductAttr(productId);

        //批量添加
        this.saveBatch(attrGroup);
        storeProductAttrValueService.saveBatch(valueGroup);

        Map<String,Object> map = new LinkedHashMap<>();
        map.put("attr",items);
        map.put("value",attrs);

        storeProductAttrResultService.insertYxStoreProductAttrResult(map,productId);
    }

    /**
     * 删除YxStoreProductAttrValue表的属性
     * @param productId 商品id
     */
    private void clearProductAttr(Long productId) {
        if(ObjectUtil.isNull(productId)) {
            throw new YshopException("产品不存在");
        }

        yxStoreProductAttrMapper.delete(Wrappers.<YxStoreProductAttr>lambdaQuery()
                .eq(YxStoreProductAttr::getProductId,productId));
        yxStoreProductAttrValueMapper.delete(Wrappers.<YxStoreProductAttrValue>lambdaQuery()
                .eq(YxStoreProductAttrValue::getProductId,productId));

    }


    /**
     * 增加库存减去销量
     * @param num 数量
     * @param productId 商品id
     * @param unique sku唯一值
     */
    @Override
    public void incProductAttrStock(Integer num, Long productId, String unique, String type ) {

        if(ProductTypeEnum.COMBINATION.getValue().equals(type)){
           yxStoreProductAttrValueMapper.incCombinationStockDecSales(num,productId,unique);
        }else if(ProductTypeEnum.SECKILL.getValue().equals(type)){
           yxStoreProductAttrValueMapper.incSeckillStockDecSales(num,productId,unique);
        }else {
            yxStoreProductAttrValueMapper.incStockDecSales(num,productId,unique);
        }
    }

    /**
     * 减少库存增加销量（针对sku操作）
     * @param num 数量
     * @param productId 商品id
     * @param unique sku唯一值
     */
    @Override
    public void decProductAttrStock(int num, Long productId, String unique,String type) {
        int res = 0;
        if(ProductTypeEnum.COMBINATION.getValue().equals(type)){
            res =  yxStoreProductAttrValueMapper.decCombinationStockIncSales(num,productId,unique);
        }else if(ProductTypeEnum.SECKILL.getValue().equals(type)){
            res =  yxStoreProductAttrValueMapper.decSeckillStockIncSales(num,productId,unique);
        }else {
            res =  yxStoreProductAttrValueMapper.decStockIncSales(num,productId,unique);
        }
        if(res == 0) {
            throw new YshopException("商品库存不足");
        }
    }



    /**
     * 更加sku 唯一值获取sku对象
     * @param unique 唯一值
     * @return YxStoreProductAttrValue
     */
    @Override
    public YxStoreProductAttrValue uniqueByAttrInfo(String unique) {
        return yxStoreProductAttrValueMapper.selectOne(Wrappers.<YxStoreProductAttrValue>lambdaQuery()
                .eq(YxStoreProductAttrValue::getUnique,unique));
    }


    /**
     * 获取商品sku属性
     * @param productId 商品id
     * @return map
     */
    @Override
    public Map<String, Object> getProductAttrDetail(long productId) {

        List<YxStoreProductAttr>  storeProductAttrs = yxStoreProductAttrMapper
                .selectList(Wrappers.<YxStoreProductAttr>lambdaQuery()
                        .eq(YxStoreProductAttr::getProductId,productId)
                        .orderByAsc(YxStoreProductAttr::getAttrValues));

        List<YxStoreProductAttrValue>  productAttrValues = storeProductAttrValueService
                .list(Wrappers.<YxStoreProductAttrValue>lambdaQuery()
                        .eq(YxStoreProductAttrValue::getProductId,productId));


        Map<String, YxStoreProductAttrValue> map = productAttrValues.stream()
                .collect(Collectors.toMap(YxStoreProductAttrValue::getSku, p -> p));

        List<YxStoreProductAttrQueryVo> yxStoreProductAttrQueryVoList = new ArrayList<>();

        for (YxStoreProductAttr attr : storeProductAttrs) {
            List<String> stringList = Arrays.asList(attr.getAttrValues().split(","));
            List<AttrValueDto> attrValueDTOS = new ArrayList<>();
            for (String str : stringList) {
                AttrValueDto attrValueDTO = new AttrValueDto();
                attrValueDTO.setAttr(str);
                attrValueDTOS.add(attrValueDTO);
            }
            YxStoreProductAttrQueryVo attrQueryVo = generator.convert(attr,YxStoreProductAttrQueryVo.class);
            attrQueryVo.setAttrValue(attrValueDTOS);
            attrQueryVo.setAttrValueArr(stringList);

            yxStoreProductAttrQueryVoList.add(attrQueryVo);
        }

        Map<String, Object> returnMap = new LinkedHashMap<>(2);
        returnMap.put("productAttr",yxStoreProductAttrQueryVoList);
        returnMap.put("productValue",map);

        return returnMap;
    }

}
