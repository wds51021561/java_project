package com.javasm.storage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.javasm.storage.entity.StorageGood;
import com.javasm.storage.mapper.StorageGoodMapper;
import com.javasm.storage.req.AddReturnGoodStorageForm;
import com.javasm.storage.service.IStorageGoodService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javasm.storage.utils.WrapperUtil;
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
public class StorageGoodServiceImpl extends ServiceImpl<StorageGoodMapper, StorageGood> implements IStorageGoodService {

}
