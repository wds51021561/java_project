package com.javasm.mapper;

import com.javasm.domin.criteria.GoodCriteria;
import com.javasm.domin.entity.Good;
import com.javasm.domin.vo.GoodVo;
import com.javasm.mapper.base.MyMapper;

import java.util.List;

public interface GoodMapper extends MyMapper<Good> {

    List<Good> searchCriteria(GoodCriteria goodCriteria);
}
