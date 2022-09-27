package com.javasm.storage.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.javasm.common.http.AxiosResult;
import com.javasm.common.page.PageResult;
import com.javasm.storage.entity.StorageAudit;
import com.javasm.storage.mapper.StorageAuditMapper;
import com.javasm.storage.req.AddAuditForm;
import com.javasm.storage.service.IStorageAuditService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 审核出入库
 * </p>
 *
 * @author yy
 * @since 2022-06-30
 */
@RestController
@RequestMapping("/storage/storageAudit")
public class StorageAuditController {


    @Autowired
    private IStorageAuditService auditService;

    @GetMapping("/reqAuditFormByStorageFormCode/{code}")
    @ApiOperation("根据库存单号查询审核记录")
    public AxiosResult reqAuditFormByStorageFormCode(@PathVariable String code) {
        List<StorageAudit> storageAudits = auditService.queryAuditByStorageFormCode(code);
        return AxiosResult.success(storageAudits);
    }

    @PostMapping("/reqAddAuditForm")
    @ApiOperation("添加审核记录")
    public AxiosResult reqAddAuditForm(@RequestBody AddAuditForm auditForm) {
        //创建审核记录
        StorageAudit storageAudit = auditService.createStorageAudit(auditForm);
        //todo 获取当前用户验证是否有审核权限,并设置审核员姓名
        storageAudit.setAuditor("测试1");
        //插入审核记录
        if (auditService.addAuditForm(storageAudit)) {
            return AxiosResult.success(1);
        }
        return AxiosResult.error(-1);
    }
}
