package com.javasm.storage.mapper;

import com.javasm.storage.entity.InventoryGood;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.javasm.storage.vo.InventoryGoodVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yy
 * @since 2022-06-30
 */
@Repository
public interface InventoryGoodMapper extends BaseMapper<InventoryGood> {

    List<InventoryGoodVO> queryInventoryGoodByStorageId(@Param("id") Integer storageId);

    List<InventoryGoodVO> queryInventoryGoodByCode(@Param("code") String inventoryCode);
}
