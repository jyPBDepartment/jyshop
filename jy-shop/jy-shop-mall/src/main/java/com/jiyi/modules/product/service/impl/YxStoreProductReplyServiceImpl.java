
package com.jiyi.modules.product.service.impl;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.jiyi.common.service.impl.BaseServiceImpl;
import com.jiyi.common.utils.QueryHelpPlus;
import com.jiyi.dozer.service.IGenerator;
import com.jiyi.enums.ShopCommonEnum;
import com.jiyi.modules.cart.vo.YxStoreCartQueryVo;
import com.jiyi.modules.product.domain.YxStoreProductReply;
import com.jiyi.modules.product.service.YxStoreProductReplyService;
import com.jiyi.modules.product.service.YxStoreProductService;
import com.jiyi.modules.product.service.dto.YxStoreProductReplyDto;
import com.jiyi.modules.product.service.dto.YxStoreProductReplyQueryCriteria;
import com.jiyi.modules.product.service.mapper.StoreProductReplyMapper;
import com.jiyi.modules.product.vo.ReplyCountVo;
import com.jiyi.modules.product.vo.YxStoreProductReplyQueryVo;
import com.jiyi.modules.user.service.YxUserService;
import com.jiyi.utils.FileUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
*
* @date 2020-05-12
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class YxStoreProductReplyServiceImpl extends BaseServiceImpl<StoreProductReplyMapper, YxStoreProductReply> implements YxStoreProductReplyService {

    @Autowired
    private IGenerator generator;

    @Autowired
    private YxUserService yxUserService;

    @Autowired
    private YxStoreProductService yxStoreProductService;


    /**
     * 评价数据
     * @param productId 商品id
     * @return ReplyCountVO
     */
    @Override
    public ReplyCountVo getReplyCount(long productId) {
        int sumCount = productReplyCount(productId);

        if(sumCount == 0) {
            return new ReplyCountVo();
        }

        //好评
        int goodCount = this.baseMapper.selectCount(Wrappers.<YxStoreProductReply>lambdaQuery()
                .eq(YxStoreProductReply::getProductId,productId)
                .eq(YxStoreProductReply::getProductScore,5));

        //中评
        int inCount = this.baseMapper.selectCount(Wrappers.<YxStoreProductReply>lambdaQuery()
                .eq(YxStoreProductReply::getProductId,productId)
                .lt(YxStoreProductReply::getProductScore,5)
                .gt(YxStoreProductReply::getProductScore,2));

        //差评
        int poorCount = this.baseMapper.selectCount(Wrappers.<YxStoreProductReply>lambdaQuery()
                .eq(YxStoreProductReply::getProductId,productId)
                .lt(YxStoreProductReply::getProductScore,2));

        //好评率
        String replyChance = ""+NumberUtil.round(NumberUtil.mul(NumberUtil.div(goodCount,sumCount),100),2);
        String replyStar = ""+NumberUtil.round(NumberUtil.mul(NumberUtil.div(goodCount,sumCount),5),2);

        return ReplyCountVo.builder()
                .sumCount(sumCount)
                .goodCount(goodCount)
                .inCount(inCount)
                .poorCount(poorCount)
                .replyChance(replyChance)
                .replySstar(replyStar)
                .build();

    }

    /**
     * 处理评价
     * @param replyQueryVo replyQueryVo
     * @return YxStoreProductReplyQueryVo
     */
    @Override
    public YxStoreProductReplyQueryVo handleReply(YxStoreProductReplyQueryVo replyQueryVo) {
        YxStoreCartQueryVo cartInfo = JSONObject.parseObject(replyQueryVo.getCartInfo()
                ,YxStoreCartQueryVo.class);
        if(ObjectUtil.isNotNull(cartInfo)){
            if(ObjectUtil.isNotNull(cartInfo.getProductInfo())){
                if(ObjectUtil.isNotNull(cartInfo.getProductInfo().getAttrInfo())){
                    replyQueryVo.setSku(cartInfo.getProductInfo().getAttrInfo().getSku());
                }
            }
        }

        BigDecimal star = NumberUtil.add(replyQueryVo.getProductScore(),
                replyQueryVo.getServiceScore());

        star = NumberUtil.div(star,2);

        replyQueryVo.setStar(String.valueOf(star.intValue()));

        if(StrUtil.isEmpty(replyQueryVo.getComment())){
            replyQueryVo.setComment("此用户没有填写评价");
        }

        return replyQueryVo;
    }

    /**
     * 获取单条评价
     * @param productId 商品di
     * @return YxStoreProductReplyQueryVo
     */
    @Override
    public YxStoreProductReplyQueryVo getReply(long productId) {
        YxStoreProductReplyQueryVo vo = this.baseMapper.getReply(productId);
        if(ObjectUtil.isNotNull(vo)){
            return handleReply(this.baseMapper.getReply(productId));
        }
        return null;
    }


    /**
     * 获取评价列表
     * @param productId 商品id
     * @param type 0-全部 1-好评 2-中评 3-差评
     * @param page page
     * @param limit limit
     * @return list
     */
    @Override
    public List<YxStoreProductReplyQueryVo> getReplyList(long productId,int type,int page, int limit) {
        List<YxStoreProductReplyQueryVo> newList = new ArrayList<>();
        Page<YxStoreProductReply> pageModel = new Page<>(page, limit);
        List<YxStoreProductReplyQueryVo> list = this.baseMapper
                .selectReplyList(pageModel,productId,type);
        List<YxStoreProductReplyQueryVo> list1 = list.stream().map(i ->{
            YxStoreProductReplyQueryVo vo = new YxStoreProductReplyQueryVo();
            BeanUtils.copyProperties(i,vo);
            if(i.getPictures().contains(",")){
                vo.setPics(i.getPictures().split(","));
            }
            return vo;
        }).collect(Collectors.toList());
        for (YxStoreProductReplyQueryVo queryVo : list1) {
            newList.add(handleReply(queryVo));
        }
        return newList;
    }

    @Override
    public int getInfoCount(Integer oid, String unique) {
       LambdaQueryWrapper<YxStoreProductReply> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(YxStoreProductReply::getUnique,unique).eq(YxStoreProductReply::getOid,oid);
        return this.baseMapper.selectCount(wrapper);
    }

    @Override
    public int productReplyCount(long productId) {

        return this.baseMapper.selectCount(Wrappers.<YxStoreProductReply>lambdaQuery()
                .eq(YxStoreProductReply::getProductId,productId));

    }

    @Override
    public int replyCount(String unique) {
       LambdaQueryWrapper<YxStoreProductReply> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(YxStoreProductReply::getUnique,unique);
        return this.baseMapper.selectCount(wrapper);
    }

    /**
     * 好评比例
     * @param productId 商品id
     * @return %
     */
    @Override
    public String replyPer(long productId) {
       LambdaQueryWrapper<YxStoreProductReply> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(YxStoreProductReply::getProductId,productId)
                .eq(YxStoreProductReply::getIsDel,ShopCommonEnum.DELETE_0.getValue())
                .eq(YxStoreProductReply::getProductScore,5);
        int productScoreCount = this.baseMapper.selectCount(wrapper);
        int count = productReplyCount(productId);
        if(count > 0){
            return ""+NumberUtil.round(NumberUtil.mul(NumberUtil.div(productScoreCount,count),100),2);
        }

        return "0";
    }





    //===================================//

    @Override
    //@Cacheable
    public Map<String, Object> queryAll(YxStoreProductReplyQueryCriteria criteria, Pageable pageable) {
        getPage(pageable);
        PageInfo<YxStoreProductReply> page = new PageInfo<>(queryAll(criteria));
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", generator.convert(page.getList(), YxStoreProductReplyDto.class));
        map.put("totalElements", page.getTotal());
        return map;
    }


    @Override
    //@Cacheable
    public List<YxStoreProductReply> queryAll(YxStoreProductReplyQueryCriteria criteria){
        List<YxStoreProductReply> storeProductReplyList =  baseMapper.selectList(QueryHelpPlus.getPredicate(YxStoreProductReply.class, criteria));
        storeProductReplyList.forEach(yxStoreProductReply->{
            yxStoreProductReply.setUser(yxUserService.getById(yxStoreProductReply.getUid()));
            yxStoreProductReply.setStoreProduct(yxStoreProductService.getById(yxStoreProductReply.getProductId()));
        });
        return storeProductReplyList;
    }


    @Override
    public void download(List<YxStoreProductReplyDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (YxStoreProductReplyDto yxStoreProductReply : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("用户ID", yxStoreProductReply.getUid());
            map.put("订单ID", yxStoreProductReply.getOid());
            map.put("唯一id", yxStoreProductReply.getUnique());
            map.put("产品id", yxStoreProductReply.getProductId());
            map.put("某种商品类型(普通商品、秒杀商品）", yxStoreProductReply.getReplyType());
            map.put("商品分数", yxStoreProductReply.getProductScore());
            map.put("服务分数", yxStoreProductReply.getServiceScore());
            map.put("评论内容", yxStoreProductReply.getComment());
            map.put("评论图片", yxStoreProductReply.getPics());
            map.put("管理员回复内容", yxStoreProductReply.getMerchantReplyContent());
            map.put("管理员回复时间", yxStoreProductReply.getMerchantReplyTime());
            map.put("0未回复1已回复", yxStoreProductReply.getIsReply());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
