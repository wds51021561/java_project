package com.javasm.service;

import com.javasm.common.page.PageResult;
import com.javasm.domin.criteria.BrandCriteria;
import com.javasm.domin.entity.Brand;
import com.javasm.domin.vo.BrandVo;
import com.javasm.service.base.BaseService;

public interface BrandService extends BaseService<Brand> {

    /**
     * 分页条件查询
     * 一个给前台传的Vo实体对象
     * 一个是封装的条件方法
     */
    PageResult<BrandVo> searchPage(BrandCriteria brandCriteria);
}
