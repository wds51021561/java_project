package com.javasm.storage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.javasm.storage.entity.StorageAudit;
import com.javasm.storage.mapper.StorageAuditMapper;
import com.javasm.storage.req.AddAuditForm;
import com.javasm.storage.service.IStorageAuditService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javasm.storage.service.IStorageFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yy
 * @since 2022-06-30
 */
@Service
@Transactional
public class StorageAuditServiceImpl extends ServiceImpl<StorageAuditMapper, StorageAudit> implements IStorageAuditService {

    @Autowired
    IStorageFormService storageFormService;

    @Autowired
    private StorageAuditMapper auditMapper;

    @Override
    public boolean addAuditForm(StorageAudit storageAudit) {
        Boolean auditFlag;
        Boolean storageFlag = true;

        //更新库存单状态
        storageFlag = storageFormService.updateStates(storageAudit.getStorageCode(), storageAudit);

        storageAudit.setModifyTime(LocalDateTime.now());
        auditFlag = saveOrUpdate(storageAudit);

        //todo 添加事务回滚判定
        return auditFlag && storageFlag;
    }

    @Override
    public StorageAudit createStorageAudit(AddAuditForm auditForm) {
        //创建审核记录
        StorageAudit storageAudit = new StorageAudit();
        //查询审核等级
        Integer level = queryAuditLevel(auditForm.getStorageCode());
        //添加审核数据
        storageAudit.setAuditLevel(level);
        storageAudit.setAuditState(auditForm.getStates());
        storageAudit.setStorageCode(auditForm.getStorageCode());
        storageAudit.setRemark(auditForm.getRemark());

        return storageAudit;
    }


    /**
     * 根据库存单号查询审核记录
     *
     * @param code
     * @return
     */
    public List<StorageAudit> queryAuditByStorageFormCode(String code) {
        LambdaQueryWrapper<StorageAudit> wrapper = new LambdaQueryWrapper<>();
        //库单编码
        wrapper.eq(StorageAudit::getStorageCode, code);
        //按审核时间排序
        wrapper.orderByDesc(StorageAudit::getModifyTime);
        return auditMapper.selectList(wrapper);
    }

    /**
     * 查询审核等级
     * @param code
     * @return
     */
    public Integer queryAuditLevel(String code){
        List<StorageAudit> storageAudits = queryAuditByStorageFormCode(code);
        if(storageAudits.size()==0){
            return 1;
        }
        StorageAudit storageAudit = storageAudits.get(0);
        Integer level = storageAudit.getAuditLevel();

        if(storageAudit.getAuditState()==0){
            return level;
        }
        level++;
        return level;
    }
}
