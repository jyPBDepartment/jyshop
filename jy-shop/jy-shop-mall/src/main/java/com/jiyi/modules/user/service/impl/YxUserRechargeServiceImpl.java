
package com.jiyi.modules.user.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.jiyi.api.YshopException;
import com.jiyi.common.service.impl.BaseServiceImpl;
import com.jiyi.common.utils.QueryHelpPlus;
import com.jiyi.dozer.service.IGenerator;
import com.jiyi.enums.BillDetailEnum;
import com.jiyi.enums.OrderInfoEnum;
import com.jiyi.enums.PayTypeEnum;
import com.jiyi.event.TemplateBean;
import com.jiyi.event.TemplateEvent;
import com.jiyi.event.TemplateListenEnum;
import com.jiyi.modules.user.domain.YxUser;
import com.jiyi.modules.user.domain.YxUserRecharge;
import com.jiyi.modules.user.service.YxUserBillService;
import com.jiyi.modules.user.service.YxUserRechargeService;
import com.jiyi.modules.user.service.dto.YxUserRechargeDto;
import com.jiyi.modules.user.service.dto.YxUserRechargeQueryCriteria;
import com.jiyi.modules.user.service.mapper.UserMapper;
import com.jiyi.modules.user.service.mapper.UserRechargeMapper;
import com.jiyi.utils.FileUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;



/**
*
* @date 2020-05-12
*/
@SuppressWarnings("unchecked")
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class YxUserRechargeServiceImpl extends BaseServiceImpl<UserRechargeMapper, YxUserRecharge> implements YxUserRechargeService {
    @Autowired
    private IGenerator generator;

    @Autowired
    private UserRechargeMapper yxUserRechargeMapper;
    @Autowired
    private YxUserBillService billService;

    @Autowired
    private UserMapper yxUserMapper;

    @Autowired
    private ApplicationEventPublisher publisher;


    @Override
    public void updateRecharge(YxUserRecharge userRecharge) {
        YxUser user = yxUserMapper.selectById(userRecharge.getUid());

        //修改状态
        userRecharge.setPaid(OrderInfoEnum.PAY_STATUS_1.getValue());
        userRecharge.setPayTime(new Date());
        yxUserRechargeMapper.updateById(userRecharge);

        //最终充值金额
        BigDecimal newPrice = NumberUtil.add(userRecharge.getPrice(),user.getNowMoney());
        newPrice = newPrice.add(userRecharge.getGivePrice());


        //增加流水
        billService.income(userRecharge.getUid(),"用户余额充值",BillDetailEnum.CATEGORY_1.getValue(),
                BillDetailEnum.TYPE_1.getValue(),userRecharge.getPrice().doubleValue(),newPrice.doubleValue(),
                "成功充值余额"+userRecharge.getPrice(),userRecharge.getId().toString());


        //update 余额
        user.setNowMoney(newPrice);
        yxUserMapper.updateById(user);

        //模板消息发布事件
        TemplateBean templateBean = TemplateBean.builder()
                .time(DateUtil.formatTime(userRecharge.getPayTime()))
                .price(userRecharge.getPrice().toString())
                .orderId(userRecharge.getOrderId())
                .uid(userRecharge.getUid())
                .templateType(TemplateListenEnum.TYPE_4.getValue())
                .build();
        publisher.publishEvent(new TemplateEvent(this, templateBean));

    }

    @Override
    public YxUserRecharge getInfoByOrderId(String orderId) {
        YxUserRecharge userRecharge = new YxUserRecharge();
        userRecharge.setOrderId(orderId);

        return yxUserRechargeMapper.selectOne(Wrappers.query(userRecharge));
    }

    /**
     * 添加充值记录
     * @param user 用户
     * @param price 充值金额
     * @param paidPrice 赠送金额
     */
    @Override
    public String addRecharge(YxUser user,String price,String paidPrice) {
        if(StrUtil.isBlank(price) || StrUtil.isBlank(paidPrice)){
            throw new YshopException("参数非法");
        }
        YxUserRecharge yxUserRecharge = new YxUserRecharge();

        String orderSn = IdUtil.getSnowflake(0,0).nextIdStr();

        yxUserRecharge.setNickname(user.getNickname());
        yxUserRecharge.setOrderId(orderSn);
        yxUserRecharge.setUid(user.getUid());
        yxUserRecharge.setPrice(new BigDecimal(price));
        yxUserRecharge.setGivePrice(new BigDecimal(paidPrice));
        yxUserRecharge.setRechargeType(PayTypeEnum.WEIXIN.getValue());
        yxUserRecharge.setPaid(OrderInfoEnum.PAY_STATUS_0.getValue());

        yxUserRechargeMapper.insert(yxUserRecharge);

        return orderSn;

    }



    //==========================================================================//

    @Override
    //@Cacheable
    public Map<String, Object> queryAll(YxUserRechargeQueryCriteria criteria, Pageable pageable) {
        getPage(pageable);
        PageInfo<YxUserRecharge> page = new PageInfo<>(queryAll(criteria));
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", generator.convert(page.getList(), YxUserRechargeDto.class));
        map.put("totalElements", page.getTotal());
        return map;
    }


    @Override
    //@Cacheable
    public List<YxUserRecharge> queryAll(YxUserRechargeQueryCriteria criteria){
        return baseMapper.selectList(QueryHelpPlus.getPredicate(YxUserRecharge.class, criteria));
    }


    @Override
    public void download(List<YxUserRechargeDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (YxUserRechargeDto yxUserRecharge : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("充值用户UID", yxUserRecharge.getUid());
            map.put("订单号", yxUserRecharge.getOrderId());
            map.put("充值金额", yxUserRecharge.getPrice());
            map.put("充值类型", yxUserRecharge.getRechargeType());
            map.put("是否充值", yxUserRecharge.getPaid());
            map.put("充值支付时间", yxUserRecharge.getPayTime());
            map.put("退款金额", yxUserRecharge.getRefundPrice());
            map.put("昵称", yxUserRecharge.getNickname());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
