package com.javasm.storage.service;

import com.javasm.common.page.PageResult;
import com.javasm.storage.entity.Inventory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.javasm.storage.query.InventoryQuery;
import com.javasm.storage.req.AddInventory;
import com.javasm.storage.vo.InventoryFromVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yy
 * @since 2022-06-30
 */
public interface IInventoryService extends IService<Inventory> {
    /**
     * 分页查询商品记录
     * @param query
     * @return
     */
    PageResult<InventoryFromVO> pageQueryInventory(InventoryQuery query);

    /**
     * 验证商品是否全部盘点
     * @param addInventory
     * @return
     */
    boolean veriGoodStorage(AddInventory addInventory);

    /**
     * 保存盘点记录
     * @param addInventory
     * @return
     */
    boolean addInventory(AddInventory addInventory);
}
