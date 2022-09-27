package com.javasm.storage.service;

import com.javasm.storage.entity.InventoryGood;
import com.baomidou.mybatisplus.extension.service.IService;
import com.javasm.storage.vo.InventoryGoodVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yy
 * @since 2022-06-30
 */
public interface IInventoryGoodService extends IService<InventoryGood> {
    /**
     * 根据厂库id查询库存记录
     * @param storageId
     * @return
     */
    List<InventoryGoodVO> queryInventoryGoodByStorageId(Integer storageId);

    /**
     * 根据盘点记录编码查询库存记录
     * @param inventoryCode
     * @return
     */
    List<InventoryGoodVO> queryInventoryGoodByCode(String inventoryCode);
}
