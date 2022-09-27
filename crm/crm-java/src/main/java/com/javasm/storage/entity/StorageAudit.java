package com.javasm.storage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.javasm.storage.utils.FormCodeUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author yy
 * @since 2022-06-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class StorageAudit implements Serializable {

    private static final long serialVersionUID = 1L;

    public StorageAudit(){
        this.auditForm = FormCodeUtil.get(FormCodeUtil.FormCodePrefix.SH);
    }

    public StorageAudit(Integer auditLevel){
        this();
        this.auditLevel = auditLevel;
    }


    /**
     * 审核id
     */

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 审核的表单编号
     */
    private String auditForm;

    /**
     * 仓库表单编号
     */
    private String storageCode;

    /**
     * 审核级别
     */
    private Integer auditLevel;

    /**
     * 审核状态
     */
    private Integer auditState;

    /**
     * 审核人
     */
    private String auditor;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime modifyTime;


}
