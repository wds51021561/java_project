package com.itheima.goods.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.goods.service.GoodsInfoService;
import com.itheima.pind.goods.dto.GoodsInfoPageDTO;
import com.itheima.pind.goods.dto.GoodsInfoSaveDTO;
import com.itheima.pind.goods.dto.GoodsInfoUpdateDTO;
import com.itheima.pind.goods.entity.GoodsInfo;
import com.itheima.pinda.base.BaseController;
import com.itheima.pinda.base.R;
import com.itheima.pinda.base.entity.SuperEntity;
import com.itheima.pinda.database.mybatis.conditions.Wraps;
import com.itheima.pinda.database.mybatis.conditions.query.LbqWrapper;
import com.itheima.pinda.dozer.DozerUtils;
import com.itheima.pinda.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@Slf4j
@RequestMapping("/goodsInfo")
@Api(value = "GoodsInfo",tags = "商品信息")
public class GoodsInfoController extends BaseController {
    @Autowired
    private DozerUtils dozer;
    @Autowired
    private GoodsInfoService goodsInfoService;

    @ApiOperation(value = "分页查询商品信息",notes = "分页查询商品信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current",value = "当前页",dataType = "long",paramType = "query",defaultValue = "1"),
            @ApiImplicitParam(name = "size",value = "每页显示几条",dataType = "long",paramType = "query",defaultValue = "10"),
    })
    @GetMapping("/page")
    @SysLog("分页查询商品信息")
    public R<IPage<GoodsInfo>> page(GoodsInfoPageDTO data){
        Page<GoodsInfo> page = getPage();
        LbqWrapper<GoodsInfo> wrapper = Wraps.lbQ();

        wrapper.like(GoodsInfo::getName,data.getName())
                .like(GoodsInfo::getCode,data.getCode())
                .eq(GoodsInfo::getBarCode,data.getBarCode())
                .geHeader(GoodsInfo::getCreateTime,data.getStartCreateTime())
                .leFooter(GoodsInfo::getCreateTime,data.getEndCreateTime())
                .orderByDesc(GoodsInfo::getCreateTime);
        goodsInfoService.page(page,wrapper);
        return success(page);
    }


    @ApiOperation(value = "查询商品信息",notes = "查询商品信息")
    @GetMapping("/list")
    @SysLog("查询商品信息")
    public R<List<GoodsInfo>> list(GoodsInfoPageDTO data){
        LbqWrapper<GoodsInfo> wrapper = Wraps.lbQ();

        wrapper.like(GoodsInfo::getName,data.getName())
                .like(GoodsInfo::getCode,data.getCode())
                .eq(GoodsInfo::getBarCode,data.getBarCode())
                .geHeader(GoodsInfo::getCreateTime,data.getStartCreateTime())
                .orderByDesc(GoodsInfo::getCreateTime);
        return success(goodsInfoService.list(wrapper));
    }

    @ApiOperation(value = "查询商品信息",notes = "查询商品信息")
    @GetMapping("/{id}")
    @SysLog("查询商品信息")
    public R<GoodsInfo> get(@PathVariable Long id){
        return success(goodsInfoService.getById(id));
    }

    @ApiOperation(value = "新增商品信息",notes = "新增商品信息不为空字段")
    @PostMapping
    @SysLog("新增商品信息")
    public R<GoodsInfo> save(@RequestBody @Validated GoodsInfoSaveDTO data){
        GoodsInfo goodsInfo = dozer.map(data,GoodsInfo.class);
        goodsInfoService.save(goodsInfo);
        return success(goodsInfo);
    }

    @ApiOperation(value = "修改商品信息",notes = "修改商品信息不能为空的字段")
    @PutMapping
    @SysLog("修改商品信息")
    public R<GoodsInfo> update(@RequestBody @Validated(SuperEntity.Update.class)GoodsInfoUpdateDTO data){
        GoodsInfo goodsInfo = dozer.map(data,GoodsInfo.class);
        goodsInfoService.updateById(goodsInfo);
        return success(goodsInfo);
    }

    @ApiOperation(value = "删除商品信息",notes = "根据id物理删除商品信息")
    @SysLog("删除商品信息")
    @DeleteMapping
    public R<Boolean> delect(@RequestParam("ids[]") List<Long> ids){
        goodsInfoService.removeByIds((ids));
        return success();
    }
}
