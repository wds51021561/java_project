package com.javasm.storage.utils.transfer;

import com.javasm.storage.entity.Inventory;
import com.javasm.storage.utils.DicUtils;
import com.javasm.storage.utils.transfer.base.TransferAble;
import com.javasm.storage.vo.InventoryFromVO;

/**
 * 作者:yy
 * 日期:2022/7/4 10:15
 * 描述:
 */
public class InventoryToVO implements TransferAble<Inventory, InventoryFromVO> {
    @Override
    public void transfer(Inventory inventory, InventoryFromVO inventoryFromVO) {
        String s = DicUtils.queryDic(5, inventory.getStorageId());
        inventoryFromVO.setStorage(s);
    }
}
