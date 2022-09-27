package com.javasm.mapper;

import com.javasm.domin.entity.Brand;
import com.javasm.mapper.base.MyMapper;
import org.apache.ibatis.annotations.Select;

public interface BrandMapper extends MyMapper<Brand> {


    @Select("select brand_name from base_brand  where id=#{id}")
    String getBrandNameById(Long id);
}
