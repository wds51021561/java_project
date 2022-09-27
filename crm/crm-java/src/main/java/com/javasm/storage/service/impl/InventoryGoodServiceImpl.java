package com.javasm.storage.service.impl;

import com.javasm.storage.entity.InventoryGood;
import com.javasm.storage.mapper.InventoryGoodMapper;
import com.javasm.storage.service.IInventoryGoodService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javasm.storage.vo.InventoryGoodVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yy
 * @since 2022-06-30
 */
@Service
@Transactional
public class InventoryGoodServiceImpl extends ServiceImpl<InventoryGoodMapper, InventoryGood> implements IInventoryGoodService {

    @Override
    public List<InventoryGoodVO> queryInventoryGoodByStorageId(Integer storageId) {
        InventoryGoodMapper mapper = getBaseMapper();
        return mapper.queryInventoryGoodByStorageId(storageId);
    }

    @Override
    public List<InventoryGoodVO> queryInventoryGoodByCode(String inventoryCode) {
        InventoryGoodMapper mapper = getBaseMapper();
        return mapper.queryInventoryGoodByCode(inventoryCode);

    }
}
