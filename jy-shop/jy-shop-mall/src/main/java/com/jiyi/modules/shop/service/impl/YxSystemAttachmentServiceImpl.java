
package com.jiyi.modules.shop.service.impl;


import com.jiyi.common.service.impl.BaseServiceImpl;
import com.jiyi.modules.shop.domain.YxSystemAttachment;
import com.jiyi.modules.shop.service.YxSystemAttachmentService;
import com.jiyi.modules.shop.service.mapper.YxSystemAttachmentMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * <p>
 * 附件管理表 服务实现类
 * </p>
 *
 *
 * @since 2019-11-11
 */
@Slf4j
@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class YxSystemAttachmentServiceImpl extends BaseServiceImpl<YxSystemAttachmentMapper, YxSystemAttachment> implements YxSystemAttachmentService {

    private final YxSystemAttachmentMapper yxSystemAttachmentMapper;

    /**
     *  根据名称获取
     * @param name name
     * @return YxSystemAttachment
     */
    @Override
    public YxSystemAttachment getInfo(String name) {
       LambdaQueryWrapper<YxSystemAttachment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(YxSystemAttachment::getName,name)
                .last("limit 1");
        return yxSystemAttachmentMapper.selectOne(wrapper);
    }

    /**
     *  根据code获取
     * @param code code
     * @return YxSystemAttachment
     */
    @Override
    public YxSystemAttachment getByCode(String code) {
       LambdaQueryWrapper<YxSystemAttachment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(YxSystemAttachment::getInviteCode,code)
                .last("limit 1");
        return yxSystemAttachmentMapper.selectOne(wrapper);
    }

    /**
     * 添加附件记录
     * @param name 名称
     * @param attSize 附件大小
     * @param attDir 路径
     * @param sattDir 路径
     */
    @Override
    public void attachmentAdd(String name, String attSize, String attDir,String sattDir) {
        YxSystemAttachment attachment =  YxSystemAttachment.builder()
                .name(name)
                .attSize(attSize)
                .attDir(attDir)
                .attType("image/jpeg")
                .sattDir(sattDir)
                .build();
        yxSystemAttachmentMapper.insert(attachment);
    }

    /**
     * 添加附件记录
     * @param name 名称
     * @param attSize 附件大小
     * @param attDir 路径
     * @param sattDir 路径
     * @param uid 用户id
     * @param code 邀请码
     */
    @Override
    public void newAttachmentAdd(String name, String attSize, String attDir, String sattDir,
                                 Long uid, String code) {

        YxSystemAttachment attachment =  YxSystemAttachment.builder()
                .name(name)
                .attSize(attSize)
                .attDir(attDir)
                .attType("image/jpeg")
                .sattDir(sattDir)
                .uid(uid)
                .inviteCode(code)
                .build();
        yxSystemAttachmentMapper.insert(attachment);
    }



}
