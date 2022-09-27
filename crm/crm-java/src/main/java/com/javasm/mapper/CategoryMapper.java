package com.javasm.mapper;

import com.javasm.domin.entity.Category;
import com.javasm.mapper.base.MyMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CategoryMapper extends MyMapper<Category> {

    @Select("select * from  base_category where category_level=#{level}")
    List<Category> getByCategoryLevel(int level);


    /**
     * 查询parentid的数据
     */
    @Select("select * from base_category where parent_id=#{parentId}")
    List<Category> getByparentId(Long parentId);

}
