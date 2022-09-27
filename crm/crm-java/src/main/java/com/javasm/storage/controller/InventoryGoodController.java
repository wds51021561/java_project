package com.javasm.storage.controller;


import com.javasm.common.http.AxiosResult;
import com.javasm.storage.service.IInventoryGoodService;
import com.javasm.storage.vo.InventoryGoodVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  库存盘点记录商品
 * </p>
 *
 * @author yy
 * @since 2022-06-30
 */
@RestController
@RequestMapping("/storage/inventoryGood")
public class InventoryGoodController {

    @Autowired
    IInventoryGoodService inventoryGoodService;
    @ApiOperation("请求查询创库中的库存记录")
    @RequestMapping("/queryInventoryGoodByStorageId/{storageId}")
    public AxiosResult queryInventoryGoodByStorageId(@PathVariable Integer storageId){
        List<InventoryGoodVO> list = inventoryGoodService.queryInventoryGoodByStorageId(storageId);
        return AxiosResult.success(list);

    }

    @ApiOperation("根据盘点编号查询盘点商品")
    @RequestMapping("/queryInventoryGoodByCode/{inventoryCode}")
    public AxiosResult queryInventoryGoodByCode(@PathVariable String inventoryCode){

        List<InventoryGoodVO> list = inventoryGoodService.queryInventoryGoodByCode(inventoryCode);
        return AxiosResult.success(list);

    }



}
