package com.javasm.service.base.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.javasm.common.reflect.ReflectionUtils;
import com.javasm.mapper.base.MyMapper;
import com.javasm.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class BaseServiceImpl<T> implements BaseService<T>{

    @Autowired
    private MyMapper<T> myMapper;

    public MyMapper<T> getMyMapper() {
        return myMapper;
    }

    @Override
    public List<T> list() {
        return myMapper.selectList(null);
    }

    @Override
    public List<T> search(Wrapper<T> wrapper) {
        return myMapper.selectList(wrapper);
    }

    @Override
    public T getById(Long id) {
        return myMapper.selectById(id);
    }

    @Override
    public int save(T t) {
        //新增的时候需要添加创建人以及创建时间
        //根据工具类的反射 调用
//        ReflectionUtils.invokeMethod(t, "setData", null, null);
        return myMapper.insert(t);
    }

    @Override
    public int update(T t) {
        //新增的时候需要添加修改人以及修改时间
        //根据工具类的反射 调用
//        ReflectionUtils.invokeMethod(t, "setData", null, null);
        return myMapper.updateById(t);
    }

    @Override
    public int deleteById(Long id) {
        return myMapper.deleteById(id);
    }

    @Override
    @Transactional
    public int batchDeleteByIds(List<Long> ids) {
        return myMapper.deleteBatchIds(ids);
    }
}
