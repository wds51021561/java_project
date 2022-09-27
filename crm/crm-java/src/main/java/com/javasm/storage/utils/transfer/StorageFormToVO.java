package com.javasm.storage.utils.transfer;

import com.javasm.storage.entity.StorageForm;
import com.javasm.storage.utils.DicUtils;
import com.javasm.storage.utils.transfer.base.TransferAble;
import com.javasm.storage.vo.StorageFormVO;

import java.util.Map;

/**
 * 作者:yy
 * 日期:2022/7/2 18:28
 * 描述: StorageForm转换器
 */
public class StorageFormToVO implements TransferAble<StorageForm, StorageFormVO> {
    @Override
    public void transfer(StorageForm storageForm, StorageFormVO storageFormVO) {
        //获取库单类型,库单状态字典
        Map<Integer, String> storageType = DicUtils.getDicList(1);
        Map<Integer, String> storageStates = DicUtils.getDicList(2);
        Map<Integer, String> storage = DicUtils.getDicList(5);
        //查询字典
        String type = storageType.get(storageForm.getStorageType());
        String states = storageStates.get(storageForm.getStorageState());
        String s = storage.get(storageForm.getStorageId());
        //更新VO对象
        storageFormVO.setStorageFormStates(states);
        storageFormVO.setStorageFormType(type);
        storageFormVO.setStorage(s);
    }
}
