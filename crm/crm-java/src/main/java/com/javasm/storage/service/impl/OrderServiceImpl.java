package com.javasm.storage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.javasm.common.page.PageResult;
import com.javasm.storage.entity.StorageForm;
import com.javasm.storage.mapper.OrderMapper;
import com.javasm.storage.query.ReturnGoodOrderQuery;
import com.javasm.storage.query.SellOrderQuery;
import com.javasm.storage.service.IOrderService;
import com.javasm.storage.vo.ReturnGoodOrderVO;
import com.javasm.storage.vo.ReturnGoodVO;
import com.javasm.storage.vo.SellGoodVO;
import com.javasm.storage.vo.SellOrderVO;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 作者:yy
 * 日期:2022/7/3 4:29
 * 描述:
 */
@Service
@Transactional
public class OrderServiceImpl implements IOrderService {

    @Autowired
    OrderMapper orderMapper;

    @Override
    public PageResult<ReturnGoodOrderVO> pageQuerySellReturnOrder(ReturnGoodOrderQuery orderQuery) {
        PageHelper.startPage(orderQuery.getCurrentPage(), orderQuery.getPageSize());
        List<ReturnGoodOrderVO> list = orderMapper.pageQuerySellReturnOrder(orderQuery);
        PageInfo pageInfo = new PageInfo(list);
        return new PageResult<>(pageInfo.getTotal(), list);
    }

    @Override
    public List<ReturnGoodVO> reqSellOrderGood(Integer id) {
        return orderMapper.findSellOrderGood(id);
    }

    @Override
    public Boolean updateOrderStates(StorageForm storageForm) {
        Integer storageType = storageForm.getStorageType();
        String orderCode = storageForm.getOrderCode();
        Integer integer;
        Boolean flag = true;
        switch (storageType){

            case 2:
                //更新销售入库订单状态
                 integer = Integer.valueOf(orderCode);
                flag = orderMapper.updateSellOrder(integer,8);
                break;
            case 7:
                integer = Integer.valueOf(orderCode);
                flag = orderMapper.updateSellOrder(integer,2);
                break;
            case 3:
                //todo 更新售后出库订单状态
                break;
            case 8:
                //todo 跟新售后入库订单状态
                break;
            default:
                break;
        }
        return flag;
    }

    @Override
    public PageResult<SellOrderVO> pageQuerySellOrder(SellOrderQuery orderQuery) {
        PageHelper.startPage(orderQuery.getCurrentPage(), orderQuery.getPageSize());
        List<SellOrderVO> list = orderMapper.pageQuerySellOrder(orderQuery);
        PageInfo pageInfo = new PageInfo(list);
        return new PageResult<>(pageInfo.getTotal(), list);
    }

    @Override
    public List<SellGoodVO> reqSellGood(Integer id) {
        return orderMapper.findSellGood(id);
    }


}

