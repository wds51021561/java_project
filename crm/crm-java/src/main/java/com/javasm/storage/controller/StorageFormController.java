package com.javasm.storage.controller;


import com.javasm.common.http.AxiosResult;
import com.javasm.common.http.EnumStatus;
import com.javasm.common.page.PageResult;
import com.javasm.storage.entity.StorageForm;
import com.javasm.storage.query.StorageFormQuery;
import com.javasm.storage.req.AddReturnGoodStorageForm;
import com.javasm.storage.service.IGoodStorageService;
import com.javasm.storage.service.IStorageFormService;
import com.javasm.storage.service.IStorageGoodService;
import com.javasm.storage.vo.StorageFormVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 出入库表
 * </p>
 *
 * @author yy
 * @since 2022-06-30
 */
@RestController
@RequestMapping("/storage/StorageForm")
public class StorageFormController {

    @Autowired
    IStorageFormService storageFormService;
    @Autowired
    IGoodStorageService goodService;

    @GetMapping("/reqStorageFormByStorageFormCode/{code}")
    @ApiOperation("通过表单编码查询表单信息")
    public AxiosResult reqStorageFormByStorageFormCode(@PathVariable String code){
        StorageForm storageForm = storageFormService.reqStorageFormByStorageFormCode(code);
        return AxiosResult.success(storageForm);
    }

    @ApiOperation("通过查询条件分页查询出入库表单")
    @GetMapping("/queryStorageForm")
    public AxiosResult reqPageStorageForm(StorageFormQuery queryForm){
       PageResult<StorageFormVO> pageResult = storageFormService.pageStorageForm(queryForm);
       return AxiosResult.success(pageResult);
    }

    @ApiOperation("请求创建销售退货的入库单")
    @PostMapping("/addSellReturnForm")
    public AxiosResult addSellReturnForm(@RequestBody AddReturnGoodStorageForm addStorageForm){
        //校验此订单编号是否已生成入库单
        if(storageFormService.isCreatedFormByCode(addStorageForm.getReturnGoodOrderCode())){
            return AxiosResult.error(EnumStatus.ERROR_FORM_CREATED);
        }
        //校验商品id与商品串码是否一致,且商品处于销售出库状态
        if (!goodService.isReturnGoodStates(addStorageForm.getGoodInfos())) {
            return AxiosResult.error(EnumStatus.ERROR_GOOD_ID);
        }
        //todo 获取当前登录人员姓名,并校验权限
        String name = "测试1";
        //创建退货入库订单并更新商品状态
        if(storageFormService.createReturnForm(addStorageForm,name)){
            return AxiosResult.success(1);
        }
        return AxiosResult.error();
    }

    @ApiOperation("请求创建销售的出库单")
    @PostMapping("/addSellForm")
    public AxiosResult addSellForm(@RequestBody AddReturnGoodStorageForm addStorageForm){

        //校验此订单编号是否已生成出库单
        if(storageFormService.isCreatedFormByCode(addStorageForm.getReturnGoodOrderCode())){
            return AxiosResult.error(EnumStatus.ERROR_FORM_CREATED);
        }
        //校验商品id与商品串码是否一致,且商品处于销售出库状态
        if (!goodService.isSellGoodStates(addStorageForm.getGoodInfos(),addStorageForm.getStorageId())) {
            return AxiosResult.error(EnumStatus.ERROR_GOOD_ID);
        }
        //todo 获取当前登录人员姓名,并校验权限
        String name = "测试1";
        //创建销售出库订单并更新商品状态
        if(storageFormService.createSellForm(addStorageForm,name)){
            return AxiosResult.success(1);
        }

        return AxiosResult.error();
    }

}
