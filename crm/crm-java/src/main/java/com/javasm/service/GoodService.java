package com.javasm.service;

import com.javasm.common.page.PageResult;
import com.javasm.domin.criteria.GoodCriteria;
import com.javasm.domin.entity.Good;
import com.javasm.domin.vo.GoodVo;
import com.javasm.service.base.BaseService;

import java.util.List;

public interface GoodService extends BaseService<Good> {
    /**
     *商品的分页条件查询
     */
    PageResult<GoodVo> searchPage(GoodCriteria goodCriteria);
}
