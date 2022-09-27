package com.javasm.storage.service;

import com.javasm.common.page.PageResult;
import com.javasm.storage.entity.StorageForm;
import com.javasm.storage.query.ReturnGoodOrderQuery;
import com.javasm.storage.query.SellOrderQuery;
import com.javasm.storage.vo.ReturnGoodOrderVO;
import com.javasm.storage.vo.ReturnGoodVO;
import com.javasm.storage.vo.SellGoodVO;
import com.javasm.storage.vo.SellOrderVO;

import java.util.List;

/**
 * 作者:yy
 * 日期:2022/7/3 4:28
 * 描述:
 */
public interface IOrderService {
    /**
     * 分页查询销售退货订单
     * @param orderQuery
     * @return
     */
    PageResult<ReturnGoodOrderVO> pageQuerySellReturnOrder(ReturnGoodOrderQuery orderQuery);

    /**
     * 查询销售退货订单商品数据
     * @param id
     * @return
     */
    List<ReturnGoodVO> reqSellOrderGood(Integer id);

    /**
     * 更新业务订单的状态
     * @param storageForm
     * @return
     */
    Boolean updateOrderStates(StorageForm storageForm);

    /**
     * 查询销售订单VO
     * @param orderQuery
     * @return
     */
    PageResult<SellOrderVO> pageQuerySellOrder(SellOrderQuery orderQuery);

    /**
     * 查询销售订单商品数据
     * @param id
     * @return
     */
    List<SellGoodVO> reqSellGood(Integer id);
}
