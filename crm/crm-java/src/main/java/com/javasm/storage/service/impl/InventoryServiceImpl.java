package com.javasm.storage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javasm.common.page.PageResult;
import com.javasm.storage.entity.Inventory;
import com.javasm.storage.entity.InventoryGood;
import com.javasm.storage.mapper.InventoryMapper;
import com.javasm.storage.query.InventoryQuery;
import com.javasm.storage.req.AddInventory;
import com.javasm.storage.service.IInventoryGoodService;
import com.javasm.storage.service.IInventoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javasm.storage.utils.FormCodeUtil;
import com.javasm.storage.utils.WrapperUtil;
import com.javasm.storage.utils.transfer.InventoryToVO;
import com.javasm.storage.utils.transfer.base.BaseTransfer;
import com.javasm.storage.vo.InventoryFromVO;
import com.javasm.storage.vo.InventoryGoodVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yy
 * @since 2022-06-30
 */
@Service
@Transactional
public class InventoryServiceImpl extends ServiceImpl<InventoryMapper, Inventory> implements IInventoryService {

    @Autowired
    IInventoryGoodService inventoryGoodService;

    @Override
    public PageResult<InventoryFromVO> pageQueryInventory(InventoryQuery query) {
        IPage<Inventory> page = new Page<>(query.getCurrentPage(), query.getPageSize());
        LambdaQueryWrapper<Inventory> wrapper = WrapperUtil.getWrapper(Inventory.class);
        if (query.getStarTime() != null && query.getEndTime() != null) {
            wrapper.between(Inventory::getCreateTime, query.getStarTime(), query.getEndTime());
        }
        if (!ObjectUtils.isEmpty(query.getStorageId())) {
            wrapper.eq(Inventory::getStorageId, query.getStorageId());
        }
        if (!StringUtils.isEmpty(query.getStorageStaff())) {
            wrapper.like(Inventory::getStorageStaff, query.getStorageStaff());
        }
        page = page(page, wrapper);
        List<Inventory> records = page.getRecords();
        List<InventoryFromVO> inventoryFromVOS = BaseTransfer.toListVO(records, InventoryFromVO.class, new InventoryToVO());
        return new PageResult<>(page.getTotal(), inventoryFromVOS);

    }

    @Override
    public boolean veriGoodStorage(AddInventory addInventory) {
        List<InventoryGoodVO> list = addInventory.getList();
        for (InventoryGoodVO vo : list) {
            //验证预计库存和实际库存是否填写
            if (ObjectUtils.isEmpty(vo.getPredict()) || ObjectUtils.isEmpty(vo.getActual())) {
                return true;
            }
            //若预计库存与实际库存不等, 且没有备注原因
            if (vo.getPredict() != vo.getActual() && StringUtils.isEmpty(vo.getRemark())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean addInventory(AddInventory addInventory) {
        //创建盘点记录
        Inventory inventory = new Inventory();
        inventory.setInventoryCode(FormCodeUtil.get(FormCodeUtil.FormCodePrefix.PD));
        inventory.setStorageId(addInventory.getStorageId());
        inventory.setStorageStaff(addInventory.getStaff());
        inventory.setRemark(addInventory.getRemark());
        inventory.setCreateTime(addInventory.getCreateTime());

        //创建盘点商品记录
        List<InventoryGoodVO> list = addInventory.getList();
        List<InventoryGood> inventoryGoods = BaseTransfer.toListVO(list, InventoryGood.class, (vo, t) -> {
            t.setId(null);
            t.setInventoryCode(inventory.getInventoryCode());
        });
        //保存记录
        boolean save = save(inventory);
        boolean b = inventoryGoodService.saveBatch(inventoryGoods);

        //todo 回滚判定
        return save && b;

    }
}
