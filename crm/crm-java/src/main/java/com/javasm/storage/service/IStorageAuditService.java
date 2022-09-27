package com.javasm.storage.service;

import com.javasm.common.http.AxiosResult;
import com.javasm.storage.entity.StorageAudit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.javasm.storage.req.AddAuditForm;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yy
 * @since 2022-06-30
 */
public interface IStorageAuditService extends IService<StorageAudit> {

    /**
     * 添加审核记录
     * @param storageAudit 审核记录
     * @return 添加是否成功
     */
    boolean addAuditForm(StorageAudit storageAudit);

    /**
     * 创建审核记录
     * @param auditForm
     * @return
     */
    StorageAudit createStorageAudit(AddAuditForm auditForm);

    /**
     * 查询库单相关的审核记录
     * @param code
     * @return
     */
    List<StorageAudit> queryAuditByStorageFormCode(String code);
}
