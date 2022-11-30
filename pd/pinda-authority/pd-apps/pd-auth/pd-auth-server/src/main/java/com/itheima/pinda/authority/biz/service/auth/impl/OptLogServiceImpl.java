package com.itheima.pinda.authority.biz.service.auth.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.pinda.authority.biz.dao.auth.OptLogMapper;
import com.itheima.pinda.authority.biz.service.auth.OptLogService;
import com.itheima.pinda.authority.entity.common.OptLog;
import com.itheima.pinda.dozer.DozerUtils;
import com.itheima.pinda.log.entity.OptLogDTO;
import org.springframework.beans.factory.annotation.Autowired;

public class OptLogServiceImpl extends ServiceImpl<OptLogMapper, OptLog> implements OptLogService {
    @Autowired
    DozerUtils dozer;

    @Override
    public Boolean save(OptLogDTO entity) {
        return super.save(dozer.map(entity,OptLog.class));
    }
}
