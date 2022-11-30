package com.itheima.pinda.authority.biz.service.auth;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.pinda.authority.entity.common.OptLog;
import com.itheima.pinda.log.entity.OptLogDTO;

public interface OptLogService extends IService<OptLog> {
    /**
     * 保存日志
     * @param entity
     * @return
     */
    Boolean save(OptLogDTO entity);
}
