package com.javasm.storage.controller;

import com.javasm.common.http.AxiosResult;
import com.javasm.common.page.PageResult;
import com.javasm.storage.query.ReturnGoodOrderQuery;
import com.javasm.storage.query.SellOrderQuery;
import com.javasm.storage.service.IOrderService;
import com.javasm.storage.vo.ReturnGoodOrderVO;
import com.javasm.storage.vo.ReturnGoodVO;
import com.javasm.storage.vo.SellGoodVO;
import com.javasm.storage.vo.SellOrderVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 作者:yy
 * 日期:2022/7/3 4:28
 * 描述:
 */
@RestController
@RequestMapping("/storage/order")
public class OrderController {

    @Autowired
    IOrderService orderService;

    @ApiOperation("查询销售退货订单")
    @GetMapping("/pageQuerySellReturnOrder")
    public AxiosResult pageQuerySellReturnOrder(ReturnGoodOrderQuery orderQuery){
        PageResult<ReturnGoodOrderVO> pageResult = orderService.pageQuerySellReturnOrder(orderQuery);
       return AxiosResult.success(pageResult);
    }
    @ApiOperation("查询销售订单商品")
    @GetMapping("/reqSellOrderGood/{id}")
    public AxiosResult reqSellOrderGood(@PathVariable Integer id){
        List<ReturnGoodVO> list = orderService.reqSellOrderGood(id);
        return AxiosResult.success(list);
    }

    @ApiOperation("查询销售订单")
    @GetMapping("/reqSellOrder")
    public AxiosResult reqSellOrder(SellOrderQuery orderQuery){
        PageResult<SellOrderVO> pageResult = orderService.pageQuerySellOrder(orderQuery);
        return AxiosResult.success(pageResult);
    }

    @ApiOperation("查询销售订单商品")
    @GetMapping("/reqSellGood/{id}")
    public AxiosResult reqSellGood(@PathVariable Integer id){
        List<SellGoodVO> list = orderService.reqSellGood(id);
        return AxiosResult.success(list);
    }
}
