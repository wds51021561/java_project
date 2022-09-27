package com.javasm.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.javasm.common.page.PageResult;
import com.javasm.domin.criteria.GoodCriteria;
import com.javasm.domin.entity.Good;
import com.javasm.domin.vo.GoodVo;
import com.javasm.mapper.GoodMapper;
import com.javasm.service.GoodService;
import com.javasm.service.base.impl.BaseServiceImpl;
import com.javasm.transfer.GoodTransfer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class GoodServiceImpl extends BaseServiceImpl<Good> implements GoodService {

    private final GoodMapper goodMapper;

    private final GoodTransfer goodTransfer;
    /**
     *商品的分页条件查询
     */
    @Override
    public PageResult<GoodVo> searchPage(GoodCriteria goodCriteria) {

        PageHelper.startPage(goodCriteria.getCurrentPage(),goodCriteria.getPageSize());
        //条件拼接 使用 xml文件写拼接
        List<Good> goodList=goodMapper.searchCriteria(goodCriteria);
        PageInfo<Good> pageInfo=new PageInfo<Good>(goodList);
        List<GoodVo> goodVoList = goodTransfer.toVO(goodList);
        return new PageResult<GoodVo>(pageInfo.getTotal(),goodVoList);
    }


}
