package com.javasm.storage.req;

import lombok.Data;
import org.springframework.lang.NonNull;

/**
 * 作者:yy
 * 日期:2022/7/1 10:39
 * 描述:
 */
@Data
public class AddAuditForm {
    /**
     * 仓库单串码
     */
    @NonNull
    private String storageCode;
    /**
     * 审核意见
     */
    @NonNull
    private String remark;
    /**
     * 审核状态
     */
    @NonNull
    private Integer states;
}
