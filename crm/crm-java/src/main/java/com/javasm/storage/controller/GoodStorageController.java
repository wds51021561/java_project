package com.javasm.storage.controller;


import com.javasm.common.http.AxiosResult;
import com.javasm.storage.entity.GoodStorage;
import com.javasm.storage.service.IGoodStorageService;
import com.javasm.storage.vo.AuditGoodVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  商品库存
 * </p>
 *
 * @author yy
 * @since 2022-06-30
 */
@RestController
@RequestMapping("/storage/goodStorage")
public class GoodStorageController {

    @Autowired
    IGoodStorageService goodStorageService;

    @ApiOperation("根据出入库表单编码查询商品库存")
    @GetMapping("/reqGoodStorageByStorageFormCode/{code}")
    public AxiosResult reqGoodStorageByStorageFormCode(@PathVariable String code){
        List<AuditGoodVO> list = goodStorageService.queryAuditGoodVoByStorageFormCode(code);
        return AxiosResult.success(list);
    }

}
