package com.javasm.storage.mapper;

import com.javasm.storage.entity.GoodStorage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.javasm.storage.vo.AuditGoodVO;
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
public interface GoodStorageMapper extends BaseMapper<GoodStorage> {

    List<GoodStorage> queryByStorageFormCode(String storageCode);

    List<AuditGoodVO> queryAuditGoodVoByStorageFormCode(String code);
}
