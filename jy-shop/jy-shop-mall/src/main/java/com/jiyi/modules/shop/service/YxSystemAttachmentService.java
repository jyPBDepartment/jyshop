
package com.jiyi.modules.shop.service;


import com.jiyi.common.service.BaseService;
import com.jiyi.modules.shop.domain.YxSystemAttachment;

/**
 * <p>
 * 附件管理表 服务类
 * </p>
 *
 *
 * @since 2019-11-11
 */
public interface YxSystemAttachmentService extends BaseService<YxSystemAttachment> {

    /**
     *  根据名称获取
     * @param name name
     * @return YxSystemAttachment
     */
    YxSystemAttachment getInfo(String name);

    /**
     *  根据code获取
     * @param code code
     * @return YxSystemAttachment
     */
    YxSystemAttachment getByCode(String code);

    /**
     * 添加附件记录
     * @param name 名称
     * @param attSize 附件大小
     * @param attDir 路径
     * @param sattDir 路径
     */
    void attachmentAdd(String name,String attSize,String attDir,String sattDir);

    /**
     * 添加附件记录
     * @param name 名称
     * @param attSize 附件大小
     * @param attDir 路径
     * @param sattDir 路径
     * @param uid 用户id
     * @param code 邀请码
     */
    void newAttachmentAdd(String name,String attSize,String attDir,String sattDir,Long uid,String code);


}
