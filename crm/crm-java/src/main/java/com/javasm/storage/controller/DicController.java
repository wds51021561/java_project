package com.javasm.storage.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.javasm.common.http.AxiosResult;
import com.javasm.storage.entity.Dic;
import com.javasm.storage.service.IDicService;
import com.javasm.storage.utils.BaseUtil;
import com.javasm.storage.vo.Options;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者:yy
 * 日期:2022/6/30 15:10
 * 描述:
 */
@RestController
@RequestMapping("/dic")
public class DicController {

    @Autowired
    IDicService dicService;




    @GetMapping("/getStorageType")
    @ApiOperation("获取仓库订单类型")
    public AxiosResult getStorageType(){

        List<Options> search = dicService.getStorageType();

        return AxiosResult.success(search);
    }

    @GetMapping("/getStorage")
    @ApiOperation("获取仓库")
    public AxiosResult getStorage(){
       List<Options> list = dicService.getStorage();
       return AxiosResult.success(list);
    }

    @GetMapping("/getOrderStates/{id}")
    @ApiOperation("获取销售订单状态")
    public AxiosResult getOrderStates(@PathVariable Integer id){
        List<Options> list = dicService.getOrderStates(id);
        return AxiosResult.success(list);
    }

    @GetMapping("/getPayType")
    @ApiOperation("获取支付方式")
    public AxiosResult getPayType(){
        List<Options> list = dicService.getPayType();
        return AxiosResult.success(list);
    }

    @GetMapping("getOrderType")
    @ApiOperation("获取订单类型")
    public AxiosResult getOrderType(){
        List<Options> list = dicService.getOrderType();
        return AxiosResult.success(list);
    }


}
