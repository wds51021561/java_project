package com.javasm.storage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.javasm.service.base.BaseService;
import com.javasm.storage.entity.Dic;
import com.javasm.storage.vo.Options;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface IDicService extends IService<Dic> {
    /**
     * 获取仓库订单类型
     * @return
     */
    List<Options> getStorageType();

    /**
     * 获取仓库类型
     * @return
     */
    List<Options> getStorage();

    /**
     * 获取销售订单类型
     * @param id 0:退货订单状态,1:销售订单状态
     * @return
     */
    List<Options> getOrderStates(Integer id);

    /**
     * 获取支付方式选项
     * @return
     */
    List<Options> getPayType();

    /**
     * 获取订单类型
     * @return
     */
    List<Options> getOrderType();
}
