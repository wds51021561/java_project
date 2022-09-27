package com.javasm.mapper;

import com.javasm.domin.entity.Dept;
import com.javasm.mapper.base.MyMapper;
import org.apache.ibatis.annotations.Select;

public interface DeptMapper extends MyMapper<Dept> {


    /**
     * 查询是否有孩子
     */
    @Select("select count(1) from base_dept where parent_id=#{id}")
    int getChildrenCount(Long id);
}
