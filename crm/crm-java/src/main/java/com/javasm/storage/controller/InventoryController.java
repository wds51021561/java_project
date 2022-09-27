package com.javasm.storage.controller;


import com.javasm.common.http.AxiosResult;
import com.javasm.common.http.EnumStatus;
import com.javasm.common.page.PageResult;
import com.javasm.storage.entity.Inventory;
import com.javasm.storage.query.InventoryQuery;
import com.javasm.storage.req.AddInventory;
import com.javasm.storage.service.IInventoryService;
import com.javasm.storage.vo.InventoryFromVO;
import com.javasm.storage.vo.InventoryGoodVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  库存盘点记录
 * </p>
 *
 * @author yy
 * @since 2022-06-30
 */
@RestController
@RequestMapping("/storage/inventory")
public class InventoryController {

    @Autowired
    private IInventoryService inventoryService;

    @ApiOperation("分页查询盘点清单")
    @GetMapping("/pageQueryInventory")
    public AxiosResult pageQueryInventory(InventoryQuery query){
        PageResult<InventoryFromVO> pageResult = inventoryService.pageQueryInventory(query);
        return AxiosResult.success(pageResult);

    }

    @ApiOperation("请求添加盘点记录")
    @PostMapping("/addInventory")
    public AxiosResult addInventory(@RequestBody AddInventory addInventory){
        //验证商品记录是否全部盘点

        if(inventoryService.veriGoodStorage(addInventory)){
            return AxiosResult.error(EnumStatus.ERROR);
        }

        //保存库存记录
        if (!inventoryService.addInventory(addInventory)) {

            return AxiosResult.error();
        }

        return AxiosResult.success(1);
    }



}
